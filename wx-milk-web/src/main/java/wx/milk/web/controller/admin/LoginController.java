package wx.milk.web.controller.admin;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import wx.exception.JsonManagerException;
import wx.milk.manager.admin.IUserManager;

import wx.milk.manager.thread.IUserThreadServer;
import wx.milk.web.utils.CookieUtils;
import wx.milk.web.utils.DESUtils;
import wx.milk.web.utils.RedisUtils;
import wx.query.Query;
import wx.query.Statement;
import wx.security.User;
import wx.util.ShiroUtils;
import wx.util.date.DateUtil;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * 登录维护类
 *
 * @author Kevin
 * <p>
 * try it,do it best!
 */
@SuppressWarnings("ALL")
@Controller
public class LoginController {

    protected Log log = LogFactory.getLog(getClass());

    @Autowired
    private IUserManager manager;
    @Autowired
    private IUserThreadServer threadServer;
    @Autowired
    private IUserManager userManager;

    @RequestMapping(value = "/login")
    public String index() {
        return "/admin/login";
    }

    @RequestMapping(value = "/")
    public String string() {
        return "/admin/login";
    }

    /**
     * 登录验证
     *
     * @return 返回的值为登录验证状态 1：验证成功
     * 0: 无效用户
     * -1：账号名或者密码错误
     * -2：验证码错误
     * @throws JsonManagerException
     */
    @RequestMapping(value = "/signin")
    @ResponseBody
    public int login(HttpSession session,
                     Query query, HttpServletResponse response,
                     String account, String password, String validatecode) throws JsonManagerException {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String sessionId = CookieUtils.getValue(request);

        // 验证验证码
        /*
        String capText = commonJedis.get(sessionId + "_sessionId");
        if(!capText.equalsIgnoreCase(validatecode)) {
            return -2;
        }*/


        User findUser = getUser(query, account);
        // 用户名或者密码错误
        if (findUser == null) {
            return -1;
        }
        // 无效用户
        if (findUser.getStatus() == 0) {
            return 0;
        }

        try {
            UsernamePasswordToken token =
                    new UsernamePasswordToken(account, DESUtils.jdkBase64String(DESUtils.encrytor(password, "sdn_ddos")));
            /*UsernamePasswordToken token =
                    new UsernamePasswordToken(account, password);*/
            token.setRememberMe(true);

            Subject subject = SecurityUtils.getSubject();
            subject.login(token);
            User user = ShiroUtils.getUser();
            session.setAttribute("user", user);
            threadServer.setIsOnline();

            String loginToken = user.getAccount() + "_" + sessionId + "_" + DateUtil.formatDateByFormat(new Date(), "yyyyMMddhhmmss");

            // 目的是挤下线
            user.setIsOnline((short) 1);

            RedisUtils.setLoginToken(user, loginToken);
            CookieUtils.setCookie(response, request, loginToken);

            return 1;
        } catch (Exception e) {
            log.error(e.getMessage());
            return -1;
        }
    }


    @RequestMapping(value = "/admin/home")
    public String home() {
        return "/common/index";
    }

    @RequestMapping("/signout")
    public String redirect(HttpServletRequest request) {
        User user = ShiroUtils.getUser();
        user.setIsOnline((short) 0);
        userManager.update(user);
        RedisUtils.deleteLoginToken(request);
        ShiroUtils.loginOut();
        return "redirect:/";
    }

    private User getUser(Query query, String account) {
        Statement statement = new Statement();
        statement.setName("account");
        statement.setValue(account);
        query.and(statement);

        return manager.findByParam(query);
    }
}

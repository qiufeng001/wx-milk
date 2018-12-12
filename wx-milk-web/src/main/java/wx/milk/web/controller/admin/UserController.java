package wx.milk.web.controller.admin;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import wx.base.controller.BaseController;
import wx.base.manager.IManager;
import wx.milk.manager.admin.IUserManager;

import wx.milk.web.utils.DESUtils;
import wx.query.Query;
import wx.security.JsonResult;
import wx.security.User;
import wx.util.ShiroUtils;


/*
 * 这个是用于维护用户信息的类
 * 
 * @author zhong.h
 *	
 * try it,do it best!
 */

@Controller
@RequestMapping("/user/*")
public class UserController extends BaseController<User, String> {

    @Autowired
    private IUserManager manager;

    @Override
    protected IManager<User, String> getManager() {
        return manager;
    }

    @Override
    protected String getTemplateFolder() {
        return "/admin/user";
    }

    /**
     * 个人资料查看接口
     * @param mav
     * @param query
     * @return
     */
    @RequestMapping("/detail")
    public ModelAndView detail(ModelAndView mav, Query query) {
        User user = ShiroUtils.getUser();
        user = manager.getByAccount(user.getAccount());
        mav.addObject("user", user);
        mav.setViewName("admin/user/detail");
        return mav;
    }

    /**
     * 编辑或者修改查询,跳转到编辑页面
     */
    @RequestMapping("/edit")
    public String edit(Model model, @RequestParam("id") String id) {
        User user = new User();
        if (!StringUtils.isEmpty(id)) {
            user = manager.findByPrimaryKey(id);
        }
        model.addAttribute(user);
        return "/admin/user/edit";
    }

    @RequestMapping("/createOrUpdate")
    @ResponseBody
    public JsonResult createOrUpdate(User user) {
        try {
            int count = validate(user);
            if(count <= 0) {
                String id = (String) user.getId();
                if (StringUtils.isEmpty(id)) {
                    // 新增
                    // 默认密码
                    String pwd = "0000";
                    String encrytor = DESUtils.jdkBase64String(DESUtils.encrytor(pwd, "sdn_ddos"));
                    user.setPassword(encrytor);
                    manager.insert(user);
                } else {
                    // 修改
                    manager.update(user);
                }
            }else {
                return new JsonResult(false,  "repeat");
            }
        } catch (Exception e) {
            logger.error(e);
            return new JsonResult(false, JsonResult.FAILED);
        }
        return new JsonResult(true, JsonResult.SUCCESS);
    }

    /**
     * 修改密码跳转
     */
    @RequestMapping(value = "/editPwd")
    @ResponseBody
    public JsonResult editPwd(Model model) {
        String userId =  ShiroUtils.getUser().getId();
        return new JsonResult(true, userId);
    }

    /**
     *  密码修改后保存
     * @param user
     * @return
     */
    @RequestMapping(value = "/updatePwd")
    @ResponseBody
    public JsonResult updatePwd(User user) {
        try {
            // 密码使用 des 加密
            String password = user.getPassword();
            user = manager.findByPrimaryKey(user.getId());
            user.setPassword(DESUtils.jdkBase64String(DESUtils.encrytor(password, "sdn_ddos")));
            manager.update(user);
            ShiroUtils.loginOut();
        }catch (Exception e) {
            logger.error(e);
        }
        // 修改密码后强制重新登录
        return new JsonResult(true, JsonResult.SUCCESS);
    }

    private Integer validate(User user) {
        Query query = new Query();
        query.where("name",user.getName());
        query.where("account",user.getAccount());
        Integer count = 0;
        try {
            count = manager.validate(query);
        }catch (Exception e) {
            logger.error(e);
        }
        return count;
    }

    /**
     * 验证用户名和账号是否重复
     */
     /*
    @RequestMapping("/validate")
    @ResponseBody
    public Integer validate(Query query) {
        Integer count = 0;
        try {
            count = manager.selectCount(query);
        }catch (Exception e) {
            logger.error(e);
        }
        return count;
    }*/
}

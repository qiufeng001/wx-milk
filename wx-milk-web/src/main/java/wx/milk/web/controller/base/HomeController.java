package wx.milk.web.controller.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import wx.base.controller.BaseController;
import wx.base.manager.IManager;
import wx.milk.manager.admin.IMenuManager;
import wx.milk.manager.admin.IUserManager;
import wx.milk.model.Menu;

import wx.milk.web.utils.MenuUtils;
import wx.query.Query;
import wx.query.Statement;
import wx.security.User;
import wx.util.ShiroUtils;

import java.util.List;

/**
 * Created by kiven on 2018/6/21/021.
 */
@Controller
@RequestMapping("/home/*")
public class HomeController extends BaseController<Menu, String> {

    @Autowired
    private IMenuManager manager;
    @Autowired
    private IUserManager userManager;

    @Override
    protected String getTemplateFolder() {
        return "";
    }

    @Override
    protected IManager getManager() {
        return manager;
    }

    @RequestMapping("/index")
    public ModelAndView index(ModelAndView mav, Query query) {
        User user = ShiroUtils.getUser();
        Statement statement = new Statement();
        statement.setName("account");
        statement.setValue(user.getAccount());
        query.and(statement);

        // 所有的权限下的菜单
        List<Menu> rootMenu = manager.selectByAccount(query);
        List<Menu> menuList = MenuUtils.getMenu(rootMenu);
        mav.addObject("menuList", menuList);
        mav.addObject("user", user);
        mav.addObject("online", userManager.getOnlineNumber());
        mav.setViewName("/common/index");
        return mav;
    }
}

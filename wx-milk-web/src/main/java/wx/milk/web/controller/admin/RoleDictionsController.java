package wx.milk.web.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import wx.base.domain.DataChangeEntry;
import wx.base.manager.IManager;
import wx.exception.JsonManagerException;
import wx.exception.manager.ManagerException;
import wx.milk.manager.admin.IDictionsManager;
import wx.milk.manager.admin.IRoleDictionsManager;
import wx.milk.manager.admin.IUserRoleManager;
import wx.milk.model.Dictions;
import wx.milk.model.RoleDictions;
import wx.milk.model.UserRole;
import wx.milk.web.utils.ControllerUtils;
import wx.query.PageResult;
import wx.query.Pagenation;
import wx.query.Query;
import wx.util.ShiroUtils;

import java.util.List;

/**
 *
 *
 * @author Kevin
 *         <p>
 *         try it,do it best!
 */
@Controller
@RequestMapping("/roleDictions/*")
public class RoleDictionsController {

    @Autowired
    private IRoleDictionsManager manager;
    @Autowired
    private IUserRoleManager userRoleManager;
    @Autowired
    private IDictionsManager dictionsManager;

    protected IManager<RoleDictions, String> getManager() {
        return manager;
    }

    protected String getTempleteFolder() {
        return "";
    }

    @ResponseBody
    @RequestMapping("/dictions")
    public List<Dictions> getDictions() {
        List<UserRole> rolesList = userRoleManager.selectByParams(ControllerUtils.getQuery(new Query(), "account", ShiroUtils.getUser().getAccount()));
        return dictionsManager.selectByParams(ControllerUtils.getQuery(new Query(), "roles",  rolesList));
    }
}

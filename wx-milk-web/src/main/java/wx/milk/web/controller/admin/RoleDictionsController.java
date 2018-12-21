package wx.milk.web.controller.admin;

import com.framework.core.query.Query;
import com.framework.core.util.ControllerUtils;
import com.framework.core.util.ShiroUtils;
import com.framework.manager.IManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import wx.milk.manager.admin.IDictionsManager;
import wx.milk.manager.admin.IRoleDictionsManager;
import wx.milk.manager.admin.IUserRoleManager;
import wx.milk.model.Dictions;
import wx.milk.model.RoleDictions;
import wx.milk.model.UserRole;
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

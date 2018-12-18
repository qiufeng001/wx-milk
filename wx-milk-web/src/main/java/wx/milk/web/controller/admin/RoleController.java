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
import wx.milk.manager.admin.IRoleManager;
import wx.milk.model.Role;
import wx.query.PageResult;
import wx.query.Pagenation;
import wx.query.Query;

import java.util.List;

/**
 *
 *
 * @author Kevin
 *         <p>
 *         try it,do it best!
 */
@Controller
@RequestMapping("/role/*")
public class RoleController {

    @Autowired
    private IRoleManager manager;

    protected String getTempleteFolder() {
        return "";
    }

    protected IManager<Role, String> getManager() {
        return manager;
    }

}

package wx.milk.web.controller.admin;

import com.framework.manager.IManager;
import com.framework.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import wx.milk.manager.admin.IRoleManager;
import wx.milk.model.Role;

/**
 *
 *
 * @author Kevin
 *         <p>
 *         try it,do it best!
 */
@Controller
@RequestMapping("/role/*")
public class RoleController extends BaseController<Role, String> {

    @Autowired
    private IRoleManager manager;

    protected IManager<Role, String> getManager() {
        return manager;
    }

    @Override
    protected String getTemplateFolder() {
        return "/admin/role";
    }
}

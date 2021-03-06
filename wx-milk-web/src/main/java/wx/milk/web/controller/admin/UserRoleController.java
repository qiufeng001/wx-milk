package wx.milk.web.controller.admin;

import com.framework.core.security.JsonResult;
import com.framework.manager.IManager;
import com.framework.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import wx.milk.manager.admin.IUserRoleManager;
import wx.milk.model.UserRole;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


/**
 * 
 *
 * @author Kevin
 *         <p>
 *         try it,do it best!
 */

@Controller
@RequestMapping("/userRole/*")
public class UserRoleController extends BaseController<UserRole, String> {

    @Autowired
    private IUserRoleManager manager;

    protected IManager<UserRole, String> getManager() {
        return manager;
    }

    @Override
    protected String getTemplateFolder() {
        return "";
    }

    @RequestMapping("/saveUserRole")
    @ResponseBody
    public JsonResult saveRoleMenu(HttpServletRequest request) {
        Map params = request.getParameterMap();
        try {
            manager.saveUserRole(params);
            return new JsonResult<>(true, JsonResult.SUCCESS);
        }catch (Exception e) {
            logger.error(e);
            return new JsonResult<>(false, JsonResult.FAILED);
        }
    }
}

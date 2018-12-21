package wx.milk.web.controller.admin;

import com.framework.core.security.JsonResult;
import com.framework.manager.IManager;
import com.framework.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import wx.milk.manager.admin.IRoleMenuManager;
import wx.milk.model.RoleMenu;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/*
 * 这个是用于维护用户信息的类
 * 
 * @author zhong.h
 *	
 * try it,do it best!
 */

@Controller
@RequestMapping("/roleMenu/*")
public class RoleMenuController extends BaseController<RoleMenu, String> {

    @Autowired
    private IRoleMenuManager manager;

    @Override
    protected IManager<RoleMenu, String> getManager() {
        return manager;
    }

    @RequestMapping(value = "/index")
    public String index1() {
        return getTemplateFolder() + "/index";
    }

    @Override
    protected String getTemplateFolder() {
        return "/admin/role";
    }

    @RequestMapping("/saveRoleMenu")
    @ResponseBody
    public JsonResult saveRoleMenu(HttpServletRequest request) {
        Map params = request.getParameterMap();
        try {
            manager.saveRoleMenu(params);
            return new JsonResult<>(true, JsonResult.SUCCESS);
        }catch (Exception e) {
            logger.error(e);
           return new JsonResult<>(false, JsonResult.FAILED);
        }
    }
}

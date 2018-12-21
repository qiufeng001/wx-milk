package wx.milk.web.controller.admin;

import com.framework.manager.IManager;
import com.framework.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import wx.milk.manager.admin.IZoneManager;
import wx.milk.model.Zone;

/**
 *
 *
 * @author Kevin
 *         <p>
 *         try it,do it best!
 */
@Controller
@RequestMapping("/zone/*")
public class ZoneController extends BaseController<Zone, String> {

    @Autowired
    private IZoneManager manager;

    @Override
    protected IManager<Zone, String> getManager() {
        return manager;
    }

    @Override
    protected String getTemplateFolder() {
        return "/admin/zone";
    }

}

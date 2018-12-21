package wx.milk.web.controller.admin;

import com.framework.manager.IManager;
import com.framework.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import wx.milk.manager.admin.IDictionsManager;
import wx.milk.model.Dictions;

/**
 *
 *
 * @author Kevin
 *         <p>
 *         try it,do it best!
 */
@Controller
@RequestMapping("/dictions/*")
public class DictionsController extends BaseController<Dictions, String> {

    @Autowired
    private IDictionsManager manager;

    @Override
    protected IManager<Dictions, String> getManager() {
        return manager;
    }

    @Override
    protected String getTemplateFolder() {
        return "/admin/dictions";
    }

}

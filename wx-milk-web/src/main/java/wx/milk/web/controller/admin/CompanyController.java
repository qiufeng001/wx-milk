package wx.milk.web.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import wx.base.controller.BaseController;
import wx.base.manager.IManager;
import wx.milk.manager.admin.ICompanyManager;
import wx.milk.manager.admin.IDictionsManager;
import wx.milk.model.Company;
import wx.milk.model.Dictions;

/**
 *
 *
 * @author Kevin
 *         <p>
 *         try it,do it best!
 */
@Controller
@RequestMapping("/company/*")
public class CompanyController extends BaseController<Company, String> {

    @Autowired
    private ICompanyManager manager;

    @Override
    protected IManager<Company, String> getManager() {
        return manager;
    }

    @Override
    protected String getTemplateFolder() {
        return "/admin/company";
    }

}

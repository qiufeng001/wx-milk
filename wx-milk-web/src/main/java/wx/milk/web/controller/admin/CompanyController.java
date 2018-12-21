package wx.milk.web.controller.admin;

import com.framework.manager.IManager;
import com.framework.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import wx.milk.manager.admin.ICompanyManager;
import wx.milk.model.Company;

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

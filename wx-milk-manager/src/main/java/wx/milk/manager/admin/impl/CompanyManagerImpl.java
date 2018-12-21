package wx.milk.manager.admin.impl;

import com.framework.manager.impl.BaseManager;
import com.framework.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import wx.milk.manager.admin.ICompanyManager;
import wx.milk.model.Company;
import wx.milk.service.admin.ICompanyService;

/**
 * @author zhong.h
 */
@Service
public class CompanyManagerImpl extends BaseManager<Company, String> implements ICompanyManager {

	@Autowired
	private ICompanyService service;

	@Override
	protected IService<Company, String> getService() {
		return service;
	}
}

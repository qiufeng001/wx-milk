package wx.milk.manager.admin.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wx.base.manager.impl.BaseManager;
import wx.base.service.IService;
import wx.milk.manager.admin.ICompanyManager;
import wx.milk.manager.admin.IZoneManager;
import wx.milk.model.Company;
import wx.milk.model.Zone;
import wx.milk.service.admin.ICompanyService;
import wx.milk.service.admin.IZoneService;

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

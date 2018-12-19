package wx.milk.service.admin.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wx.base.domain.IRepository;
import wx.base.service.impl.BaseService;
import wx.milk.domain.CompanyRepository;
import wx.milk.domain.DictionsRepository;
import wx.milk.model.Company;
import wx.milk.model.Zone;
import wx.milk.service.admin.ICompanyService;
import wx.milk.service.admin.IZoneService;

@Service
public class CompanyServiceImpl extends BaseService<Company, String> implements ICompanyService {

	@Autowired
	private CompanyRepository repository;

	@Override
	protected IRepository getRepository() {
		return repository;
	}

}

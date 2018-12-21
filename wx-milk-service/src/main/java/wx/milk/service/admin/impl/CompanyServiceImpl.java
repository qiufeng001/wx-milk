package wx.milk.service.admin.impl;

import com.framework.core.base.domain.IRepository;
import com.framework.service.impl.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wx.milk.domain.CompanyRepository;
import wx.milk.model.Company;
import wx.milk.service.admin.ICompanyService;

@Service
public class CompanyServiceImpl extends BaseService<Company, String> implements ICompanyService {

	@Autowired
	private CompanyRepository repository;

	@Override
	protected IRepository getRepository() {
		return repository;
	}

}

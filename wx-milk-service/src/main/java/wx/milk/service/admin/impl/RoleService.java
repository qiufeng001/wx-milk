package wx.milk.service.admin.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.framework.core.base.domain.IRepository;
import com.framework.service.impl.BaseService;
import wx.milk.domain.RoleRepository;
import wx.milk.model.Role;
import wx.milk.service.admin.IRoleService;

@Service
public class RoleService extends BaseService<Role, String> implements IRoleService {

	@Autowired
	private RoleRepository repository;

	@Override
	protected IRepository getRepository() {
		return repository;
	}

}

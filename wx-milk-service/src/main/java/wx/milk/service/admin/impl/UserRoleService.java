package wx.milk.service.admin.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wx.base.domain.IRepository;
import wx.base.service.impl.BaseService;
import wx.milk.domain.UserRoleRepository;
import wx.milk.model.UserRole;
import wx.milk.service.admin.IUserRoleService;

@Service
public class UserRoleService extends BaseService<UserRole, String> implements IUserRoleService {

	@Autowired
	private UserRoleRepository repository;

	@Override
	protected IRepository getRepository() {
		return repository;
	}

	@Override
	public Integer validateUserRole(String roleNo, String accound) {
		return repository.validateUserRole(roleNo, accound);
	}
}

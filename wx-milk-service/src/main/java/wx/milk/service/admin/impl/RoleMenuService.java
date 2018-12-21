package wx.milk.service.admin.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.framework.core.base.domain.IRepository;
import com.framework.service.impl.BaseService;
import wx.milk.domain.RoleMenuRepository;
import wx.milk.model.RoleMenu;
import wx.milk.service.admin.IRoleMenuService;

@Service
public class RoleMenuService extends BaseService<RoleMenu, String> implements IRoleMenuService {

	@Autowired
	private RoleMenuRepository repository;

	@Override
	protected IRepository getRepository() {
		return repository;
	}

	@Override
	public Integer validateRoleMenu(String roleNo, String menuId) {
		return repository.validateRoleMenu(roleNo, menuId);
	}
}

package wx.milk.manager.admin.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wx.base.service.IService;
import wx.milk.manager.admin.IRoleManager;
import wx.milk.manager.impl.BaseManager;
import wx.milk.model.Role;
import wx.milk.service.admin.IRoleService;


@Service
public class RoleManager extends BaseManager<Role, String> implements IRoleManager {

	@Autowired
	private IRoleService service;

	@Override
	protected IService<Role, String> getService() {
		return service;
	}

}

package wx.milk.manager.admin.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wx.base.service.IService;
import wx.exception.manager.ManagerException;
import wx.milk.manager.admin.IRoleMenuManager;
import wx.base.manager.impl.BaseManager;
import wx.milk.model.RoleMenu;
import wx.milk.service.admin.IRoleMenuService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class RoleMenuManager extends BaseManager<RoleMenu, String> implements IRoleMenuManager {

	@Autowired
	private IRoleMenuService service;

	@Override
	protected IService<RoleMenu, String> getService() {
		return service;
	}


	@Override
	public Integer saveRoleMenu(Map parmas) {

		// 查出所以的撺掇
		String[] roles = (String[])parmas.get("roles[]");
		List<RoleMenu> list = new ArrayList<>();
		String[] menuIds = (String[]) parmas.get("menuId");
		try {
			for (int i = 0; i < roles.length; i++) {
				String menuId = menuIds[0];
				String role = roles[i];
				int validate = service.validateRoleMenu(role, menuId);
				if(validate <= 0) {
					RoleMenu roleMenu = new RoleMenu();
					roleMenu.setMenuId(menuId);
					roleMenu.setRoleNo(role);
					list.add(roleMenu);
				}
			}
			return service.batchesInsert(list);
		} catch (Exception e) {
			throw new ManagerException(e);
		}
	}
}

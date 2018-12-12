package wx.milk.manager.admin.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wx.base.service.IService;
import wx.exception.manager.ManagerException;
import wx.milk.manager.admin.IUserRoleManager;
import wx.milk.manager.impl.BaseManager;
import wx.milk.model.UserRole;
import wx.milk.service.admin.IUserRoleService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class UserRoleManager extends BaseManager<UserRole, String> implements IUserRoleManager {

	@Autowired
	private IUserRoleService service;

	@Override
	protected IService<UserRole, String> getService() {
		return service;
	}

	@Override
	public Integer saveUserRole(Map parmas) {
		// 查出所以的撺掇
		String[] roles = (String[])parmas.get("roles[]");
		List<UserRole> list = new ArrayList<>();
		String[] accounts = (String[]) parmas.get("account");
		try {
			for (int i = 0; i < roles.length; i++) {
				String account = accounts[0];
				String role = roles[i];
				int validate = service.validateUserRole(role, account);
				if(validate <= 0) {
					UserRole userRole = new UserRole();
					userRole.setAccount(account);
					userRole.setRoleNo(role);
					userRole.setStatus((short)1);
					list.add(userRole);
				}
			}
			return service.batchesInsert(list);
		} catch (Exception e) {
			throw new ManagerException(e);
		}
	}
}

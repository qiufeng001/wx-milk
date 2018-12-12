package wx.milk.manager.admin.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wx.base.service.IService;
import wx.exception.manager.ManagerException;
import wx.milk.manager.admin.IUserManager;
import wx.milk.manager.impl.BaseManager;
import wx.milk.model.Role;
import wx.milk.service.admin.IUserService;
import wx.security.User;


@Service
public class UserManager extends BaseManager<User, String> implements IUserManager {

	private Log LOG = LogFactory.getLog(getClass());

	@Autowired
	private IUserService service;

	@Override
	protected IService<User, String> getService() {
		return service;
	}

	@Override
	public User getByAccount(String account) {
		User user = null;
		try {
			user = service.getByAccount(account);
			List<Role> roles = service.listByAccount(account);
			//user.setRoles(roles);
		}catch (ManagerException e) {
			LOG.error(e);
		}
		return user;
	}

	@Override
	public Integer getOnlineNumber() {
		return service.getOnlineNumber();
	}


}

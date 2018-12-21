package wx.milk.manager.admin.impl;

import java.util.List;

import com.framework.core.exception.manager.ManagerException;
import com.framework.core.security.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.framework.manager.impl.BaseManager;
import com.framework.service.IService;
import wx.milk.manager.admin.IUserManager;
import wx.milk.model.Role;
import wx.milk.service.admin.IUserService;


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

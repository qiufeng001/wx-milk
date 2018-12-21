package wx.milk.service.admin.impl;

import java.util.List;

import com.framework.core.security.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.framework.core.base.domain.IRepository;
import com.framework.service.impl.BaseService;
import wx.milk.domain.UserRepository;
import wx.milk.model.Role;

import wx.milk.service.admin.IUserService;


@Service
public class UserService extends BaseService<User, String> implements IUserService {

	@Autowired
	private UserRepository repository;
	
	@Override
	protected IRepository getRepository() {
		return repository;
	}

	@Override
	public User getByAccount(String account) {
		return repository.getByAccount(account);
	}

	@Override
	public List<Role> listByAccount(String account) {
		return repository.listByAccount(account);
	}

	@Override
	public Integer getOnlineNumber() {
		return repository.getOnlineNumber();
	}
}

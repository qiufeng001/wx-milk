package wx.milk.service.admin.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wx.base.domain.IRepository;
import wx.base.service.impl.BaseService;
import wx.milk.domain.MenuRepository;
import wx.milk.model.Menu;
import wx.milk.service.admin.IMenuService;
import wx.query.Query;

import java.util.List;

@Service
public class MenuService extends BaseService<Menu, String> implements IMenuService {

	@Autowired
	private MenuRepository repository;

	@Override
	protected IRepository getRepository() {
		return repository;
	}

	@Override
	public List<Menu> selectByAccount(Query query) {
		return repository.selectByAccount(query.asMap());
	}

	@Override
	public List<Menu> selectPid() {
		return repository.selectPid();
	}
}

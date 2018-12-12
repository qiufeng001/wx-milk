package wx.milk.manager.admin.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wx.base.service.IService;
import wx.milk.manager.admin.IMenuManager;
import wx.milk.manager.impl.BaseManager;
import wx.milk.model.Menu;
import wx.milk.service.admin.IMenuService;
import wx.query.Query;

import java.util.List;


@Service
public class MenuManager extends BaseManager<Menu, String> implements IMenuManager {

	@Autowired
	private IMenuService service;

	@Override
	protected IService<Menu, String> getService() {
		return service;
	}

	@Override
	public List<Menu> selectByAccount(Query query) {
		return service.selectByAccount(query);
	}

	@Override
	public List<Menu> selectPid() {
		return service.selectPid();
	}
}

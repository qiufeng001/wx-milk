package wx.milk.manager.admin.impl;

import com.framework.core.query.Query;
import com.framework.manager.impl.BaseManager;
import com.framework.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wx.milk.manager.admin.IMenuManager;
import wx.milk.model.Menu;
import wx.milk.service.admin.IMenuService;

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

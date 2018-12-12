package wx.milk.manager.admin.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wx.base.service.IService;
import wx.milk.manager.admin.IDictionsManager;
import wx.milk.manager.impl.BaseManager;
import wx.milk.model.Dictions;
import wx.milk.service.admin.IDictionsService;
import wx.query.Query;

import java.util.List;


@Service
public class DictionsManager extends BaseManager<Dictions, String> implements IDictionsManager {

	@Autowired
	private IDictionsService service;

	@Override
	protected IService<Dictions, String> getService() {
		return service;
	}

	@Override
	public List<Dictions> selectDictions(Query query) {
		return service.selectDictions(query);
	}
}

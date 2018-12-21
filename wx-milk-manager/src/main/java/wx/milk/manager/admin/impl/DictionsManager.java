package wx.milk.manager.admin.impl;

import com.framework.core.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.framework.manager.impl.BaseManager;
import com.framework.service.IService;
import wx.milk.manager.admin.IDictionsManager;
import wx.milk.model.Dictions;
import wx.milk.service.admin.IDictionsService;

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

package wx.milk.manager.admin.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.framework.manager.impl.BaseManager;
import com.framework.service.IService;
import wx.milk.manager.admin.IRoleDictionsManager;
import wx.milk.model.RoleDictions;
import wx.milk.service.admin.IRoleDictionsService;


@Service
public class RoleDictionsManager extends BaseManager<RoleDictions, String> implements IRoleDictionsManager {

	@Autowired
	private IRoleDictionsService service;

	@Override
	protected IService<RoleDictions, String> getService() {
		return service;
	}

}

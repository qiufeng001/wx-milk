package wx.milk.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wx.base.service.IService;
import wx.milk.manager.IWxJobManager;
import wx.milk.model.WxJob;
import wx.milk.service.IWxJobService;


@Service
public class WxJobManager extends BaseManager<WxJob, String> implements IWxJobManager {

	@Autowired
	private IWxJobService service;

	@Override
	protected IService<WxJob, String> getService() {
		return service;
	}

}

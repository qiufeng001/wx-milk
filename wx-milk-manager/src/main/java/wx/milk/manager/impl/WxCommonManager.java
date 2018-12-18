package wx.milk.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wx.base.manager.impl.BaseManager;
import wx.base.service.IService;
import wx.exception.manager.ManagerException;
import wx.milk.manager.IWxCommonManager;
import wx.milk.model.WxCommon;
import wx.milk.service.IWxCommonService;
import wx.query.Query;

@Service
public class WxCommonManager extends BaseManager<WxCommon, String> implements IWxCommonManager {

	@Autowired
	private IWxCommonService service;

	@Override
	protected IService<WxCommon, String> getService() {
		return service;
	}

	@Override
	public WxCommon findMaxSequence(Query query) throws ManagerException {
		return service.findMaxSequence(query);
	}
}

package wx.milk.manager.admin.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wx.base.manager.impl.BaseManager;
import wx.base.service.IService;
import wx.milk.manager.admin.IDictionsManager;
import wx.milk.manager.admin.IZoneManager;
import wx.milk.model.Dictions;
import wx.milk.model.Zone;
import wx.milk.service.admin.IDictionsService;
import wx.milk.service.admin.IZoneService;
import wx.query.Query;

import java.util.List;

/**
 * @author zhong.h
 */
@Service
public class ZoneManagerImpl extends BaseManager<Zone, String> implements IZoneManager {

	@Autowired
	private IZoneService service;

	@Override
	protected IService<Zone, String> getService() {
		return service;
	}
}

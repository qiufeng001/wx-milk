package wx.milk.manager.admin.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.framework.manager.impl.BaseManager;
import com.framework.service.IService;
import wx.milk.manager.admin.IZoneManager;
import wx.milk.model.Zone;
import wx.milk.service.admin.IZoneService;

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

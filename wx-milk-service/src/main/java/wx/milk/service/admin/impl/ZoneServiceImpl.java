package wx.milk.service.admin.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.framework.core.base.domain.IRepository;
import com.framework.service.impl.BaseService;
import wx.milk.domain.DictionsRepository;
import wx.milk.model.Zone;
import wx.milk.service.admin.IZoneService;

import java.util.List;

@Service
public class ZoneServiceImpl extends BaseService<Zone, String> implements IZoneService {

	@Autowired
	private DictionsRepository repository;

	@Override
	protected IRepository getRepository() {
		return repository;
	}

}

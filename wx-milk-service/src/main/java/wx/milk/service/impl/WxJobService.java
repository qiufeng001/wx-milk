package wx.milk.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wx.base.domain.IRepository;
import wx.base.service.impl.BaseService;
import wx.milk.domain.WxJobRepository;
import wx.milk.model.WxJob;
import wx.milk.service.IWxJobService;

@Service
public class WxJobService extends BaseService<WxJob, String> implements IWxJobService {

	@Autowired
	private WxJobRepository repository;

	@Override
	protected IRepository getRepository() {
		return repository;
	}

}

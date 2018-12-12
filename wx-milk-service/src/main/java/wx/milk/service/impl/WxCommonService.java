package wx.milk.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wx.base.domain.IRepository;
import wx.base.service.impl.BaseService;
import wx.milk.domain.WxCommonRepository;
import wx.milk.domain.WxJobRepository;
import wx.milk.model.WxCommon;
import wx.milk.model.WxJob;
import wx.milk.service.IWxCommonService;
import wx.milk.service.IWxJobService;
import wx.query.Query;

import java.util.Map;

@Service
public class WxCommonService extends BaseService<WxCommon, String> implements IWxCommonService {

	@Autowired
	private WxCommonRepository repository;

	@Override
	protected IRepository getRepository() {
		return repository;
	}

	@Override
	public WxCommon findMaxSequence(Query query) {
		return repository.findMaxSequence(query.asMap());
	}
}

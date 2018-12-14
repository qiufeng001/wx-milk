package wx.milk.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wx.base.domain.IRepository;
import wx.base.service.impl.BaseService;
import wx.milk.domain.SystemLogRepository;
import wx.milk.domain.WxCommonRepository;
import wx.milk.model.WxCommon;
import wx.milk.model.service.SystemLog;
import wx.milk.service.ISystemLogService;
import wx.milk.service.IWxCommonService;
import wx.query.Query;

@Service
public class SystemLogServiceImpl extends BaseService<SystemLog, String> implements ISystemLogService {

	@Autowired
	private SystemLogRepository repository;

	@Override
	protected IRepository getRepository() {
		return repository;
	}

}

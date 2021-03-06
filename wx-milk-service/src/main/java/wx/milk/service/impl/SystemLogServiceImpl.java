package wx.milk.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.framework.core.base.domain.IRepository;
import com.framework.service.impl.BaseService;
import wx.milk.domain.SystemLogRepository;
import wx.milk.model.log.SystemLog;
import wx.milk.service.ISystemLogService;

@Service
public class SystemLogServiceImpl extends BaseService<SystemLog, String> implements ISystemLogService {

	@Autowired
	private SystemLogRepository repository;

	@Override
	protected IRepository getRepository() {
		return repository;
	}

}

package wx.milk.service.admin.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.framework.core.base.domain.IRepository;
import com.framework.service.impl.BaseService;
import wx.milk.domain.RoleDictionsRepository;
import wx.milk.model.RoleDictions;
import wx.milk.service.admin.IRoleDictionsService;

@Service
public class RoleDictionsService extends BaseService<RoleDictions, String> implements IRoleDictionsService {

	@Autowired
	private RoleDictionsRepository repository;

	@Override
	protected IRepository getRepository() {
		return repository;
	}

}

package wx.milk.manager.impl;

import com.framework.core.exception.manager.ManagerException;
import com.framework.core.query.Query;
import com.framework.manager.ICommonManager;
import com.framework.model.Common;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.framework.manager.impl.BaseManager;
import com.framework.service.IService;
import wx.milk.service.IWxCommonService;

@Service
public class WxCommonManager extends BaseManager<Common, String> implements ICommonManager {

	@Autowired
	private IWxCommonService service;

	@Override
	protected IService<Common, String> getService() {
		return service;
	}

	@Override
	public Common findMaxSequence(Query query) throws ManagerException {
		return service.findMaxSequence(query);
	}
}

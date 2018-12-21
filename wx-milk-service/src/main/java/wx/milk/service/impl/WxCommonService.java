package wx.milk.service.impl;

import com.framework.core.query.Query;
import com.framework.model.Common;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.framework.core.base.domain.IRepository;
import com.framework.service.impl.BaseService;
import wx.milk.domain.WxCommonRepository;
import wx.milk.service.IWxCommonService;

@Service
public class WxCommonService extends BaseService<Common, String> implements IWxCommonService {

	@Autowired
	private WxCommonRepository repository;

	@Override
	protected IRepository getRepository() {
		return repository;
	}

	@Override
	public Common findMaxSequence(Query query) {
		return repository.findMaxSequence(query.asMap());
	}
}

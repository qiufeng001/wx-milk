package wx.milk.service.admin.impl;

import com.framework.core.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.framework.core.base.domain.IRepository;
import com.framework.service.impl.BaseService;
import wx.milk.domain.DictionsRepository;
import wx.milk.model.Dictions;
import wx.milk.service.admin.IDictionsService;

import java.util.List;

@Service
public class DictionsService extends BaseService<Dictions, String> implements IDictionsService {

	@Autowired
	private DictionsRepository repository;

	@Override
	protected IRepository getRepository() {
		return repository;
	}

	@Override
	public List<Dictions> selectDictions(Query query) {
		return repository.selectDictions(query.asMap());
	}
}

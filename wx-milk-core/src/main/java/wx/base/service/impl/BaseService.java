package wx.base.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import wx.base.domain.IRepository;
import wx.base.manager.IEntryResultHandler;
import wx.base.service.IService;
import wx.log.SystemServiceLog;
import wx.query.Page;
import wx.query.Pagenation;
import wx.query.Query;
import wx.security.IEntity;

import java.util.List;

public abstract class BaseService<T extends IEntity, K> implements IService<T, K> {

	protected Log logger = LogFactory.getLog(getClass());

	protected abstract IRepository<T, K> getRepository();

	@Override
	@Transactional(readOnly=true)
	public T findByPrimaryKey(K id) {
		return getRepository().findByPrimaryKey(id);
	}

	@Override
	@Transactional(readOnly=true)
	public T findByParam(Query query) {
		return getRepository().findByParam(query.asMap());
	}

	@Override
	@Transactional(readOnly=true)
	public Integer selectCount(Query query) {
		return getRepository().selectCount(query.asMap());
	}

	@Override
	@Transactional(readOnly=true)
	public List<T> selectByPage(Query query, Pagenation page) {
		return getRepository().selectByPage(query.asMap(), page, query.getSort());
	}

	@Override
	@Transactional(readOnly=true)
	public List<T> selectByParams(Query query) {
		return getRepository().selectByParams(query.asMap(), query.getSort());
	}

	@Override
	@Transactional(readOnly=true)
	public void selectByParams(Query query, final IEntryResultHandler<T> handler) {

		ResultHandler<T> resultHandler = new ResultHandler<T>() {
			@Override
			public void handleResult(ResultContext<? extends T> resultContext) {
				boolean ok = handler.handleResult(resultContext.getResultObject());
				if (!ok) {
					resultContext.stop();
				}
			}
		};

		getRepository().selectByParams(query.asMap(), query.getSort(), resultHandler);
	}

	@Override
	@Transactional(readOnly=false, isolation = Isolation.READ_COMMITTED, rollbackFor=Exception.class)
	public Integer insert(T entry) {
		int i = 1 / 0;
		return getRepository().insert(entry);
	}

	@Override
	@Transactional(readOnly=false, isolation = Isolation.READ_COMMITTED, rollbackFor=Exception.class)
	public Integer batchesInsert(List<T> list) {
		int count = 0;
		for (T entry : list) {

			getRepository().insert(entry);
			count += 1;
		}
		return count;
	}

	@Override
	@Transactional(readOnly=false, isolation = Isolation.READ_COMMITTED, rollbackFor=Exception.class)
	public Integer update(T entry) {
		return getRepository().update(entry);
	}

	@Override
	@Transactional(readOnly=false, isolation = Isolation.READ_COMMITTED, rollbackFor=Exception.class)
	public Integer deleteByParams(Query query) {
		return getRepository().deleteByParams(query.asMap());
	}

	@Override
	@Transactional(readOnly=false, isolation = Isolation.READ_COMMITTED, rollbackFor=Exception.class)
	public Integer deleteByPrimaryKey(K id) {
		return getRepository().deleteByPrimaryKey(id);
	}

	@Override
	@Transactional(readOnly=true)
	public Integer validate(Query query) {
		return getRepository().validate(query.asMap());
	}

}

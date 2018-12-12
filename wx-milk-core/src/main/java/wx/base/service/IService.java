package wx.base.service;

import wx.base.manager.IEntryResultHandler;
import wx.query.Pagenation;
import wx.query.Query;
import wx.security.IEntity;

import java.util.List;

public interface IService<T extends IEntity, K> {

	public T findByPrimaryKey(K id);

	public T findByParam(Query query);

	public Integer selectCount(Query query);

	public List<T> selectByPage(Query query,Pagenation page);

	public List<T> selectByParams(Query query);

	public void selectByParams(Query query,IEntryResultHandler<T> handler );

	public Integer insert(T entry);

	public Integer batchesInsert(List<T> list);

	public Integer update(T entry);

	public Integer deleteByParams(Query query);

	public Integer deleteByPrimaryKey(K id);

	public Integer validate(Query query);
	
}

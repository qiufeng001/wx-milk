package wx.base.manager;

import wx.exception.manager.ManagerException;
import wx.query.Pagenation;
import wx.query.Query;
import wx.security.IEntity;

import java.util.List;


public interface IManager<T extends IEntity, K> {

	public T findByPrimaryKey(K id) throws ManagerException;

	public T findByParam(Query query) throws ManagerException;

	public Integer selectCount(Query query) throws ManagerException;

	public List<T> selectByPage(Query query, Pagenation page) throws ManagerException;

	public List<T> selectByParams(Query query) throws ManagerException;

	public void selectByParams(Query query, IEntryResultHandler<T> handler);

	public Integer insert(T entry) throws ManagerException;

	public Integer update(T entry) throws ManagerException;

	public Integer deleteByParams(Query query) throws ManagerException;

	public Integer deleteByPrimaryKey(K id) throws ManagerException;

	public Integer batchSave(List<T> inserted,List<T> updated,List<T> deleted) throws ManagerException;
	/**
	 * 通过ID批量删除:先通过参数查询出结果,再用ID一个个删除,避免多并发下死锁
	 * @param query
	 * @return
	 */
	int batchDeleteByParams(Query query);

	public Integer validate(Query query) throws ManagerException;
}

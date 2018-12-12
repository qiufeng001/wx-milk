package wx.base.controller;

import wx.query.Page;
import wx.query.PageResult;
import wx.query.Pagenation;
import wx.query.Query;
import wx.security.IEntity;

import java.util.List;


/**
 * 
 * 控制层接口
 * 
 * @author zhonghui
 *
 * @param <T>
 * @param <K>
 */
public interface IController<T extends IEntity, K> {

	public T create(T entity);

 	public T update(T entity);

 	public Integer deleteByParams(Query query);
 	
 	public Integer deleteByPrimaryKey(K id);
	
	public T findByParam(Query query);
	
	public T findByPrimaryKey(K id);

	public List<T> selectByParams(Query query);
	
	public PageResult<T> selectByPage(Query query, Pagenation page);
	
}

package wx.base.domain;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.ResultHandler;
import wx.query.Page;
import wx.query.Pagenation;
import wx.security.IEntity;

import java.util.List;
import java.util.Map;


/**
 * 这里是公共基础的持久层接口，定义的是公共的基础增删改查功能
 * @author Kevin
 *	
 * try it,do it best!
 */
public interface IRepository<T extends IEntity,K> {
	public T findByPrimaryKey(K id);

	public T findByParam(@Param("params") Map<String, Object> params);

	public Integer selectCount(@Param("params") Map<String, Object> params);

	public List<T> selectByPage(@Param("params") Map<String, Object> params,
								@Param("page") Pagenation page,
								@Param("orderby") String orderby);

	public List<T> selectByParams(@Param("params") Map<String, Object> params,@Param("orderby") String orderby);

	public void selectByParams(@Param("params") Map<String, Object> params,@Param("orderby") String orderby,ResultHandler<T> handler);

	public Integer insert(T entry);

	public Integer update(T entry);

	public Integer deleteByParams(@Param("params") Map<String, Object> params);

	public Integer deleteByPrimaryKey(K id);

	/**
	 * 验证
	 */
	public Integer validate(@Param("params") Map<String, Object> params);
}

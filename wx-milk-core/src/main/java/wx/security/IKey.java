package wx.security;

/**
 * 主键接口定义
 * 
 * 这里的 T 指的是无论传的是什么类型
 * @author Kevin
 *	
 * try it,do it best!
 */
public interface IKey<T> {
	public T getId();
	public void setId(T id);
}

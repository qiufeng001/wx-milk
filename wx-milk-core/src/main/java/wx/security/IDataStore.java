package wx.security;

public interface IDataStore {
	
	public Object get(String key);
	
	public void set(String key, Object obj);
	
	/**
	 * 
	 * @param key
	 * @param obj
	 * @param expire 单位秒
	 */
	public void set(String key, Object obj, long expire);

	public void remove(String key);
	
	public void invalidate();
}

package wx.cache;

import java.util.List;
import java.util.Set;

import wx.base.ProcessStatus;

public interface ICacheProvider {
	/**
	 * 缓存范围:local,global
	 * 
	 * @return
	 */
	public String getScope();

	public String getName();

	public String getGroup();

	public <T> T get(String key);

	public Set<Object> keys();

	public <T> List<T> getAll(String... keys);

	public void set(String key, Object obj);

	public void remove(String key);

	public void clear();

	public void load();

	public Object load(String key);

	public ProcessStatus getStatus();
}

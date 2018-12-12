package wx.base.domain;

import wx.security.IEntity;

import java.io.Serializable;
import java.util.List;

public class DataChangeEntry<T extends IEntity> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3823587629139663428L;
	
	private List<T> inserted;
	private List<T> updated;
	private List<T> deleted;
	public List<T> getInserted() {
		return inserted;
	}
	public void setInserted(List<T> inserted) {
		this.inserted = inserted;
	}
	public List<T> getUpdated() {
		return updated;
	}
	public void setUpdated(List<T> updated) {
		this.updated = updated;
	}
	public List<T> getDeleted() {
		return deleted;
	}
	public void setDeleted(List<T> deleted) {
		this.deleted = deleted;
	}
	
	
	
}

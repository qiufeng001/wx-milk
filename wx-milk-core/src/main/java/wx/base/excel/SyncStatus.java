package wx.base.excel;

import java.io.Serializable;

import wx.base.UUID;

public class SyncStatus implements Serializable {

	String id;

	public SyncStatus() {
		this.id = UUID.newUUID().toString();
	}

	public SyncStatus(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -3968330513152965649L;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	private int count = 0;
	private int index = 0;
	private String msg;
	private int step = 0;
	private int status = 1;
	private String title;
	private String tag;

	public void nextStep(int count, String title) {
		this.count = count;
		index = 0;
		step += 1;
		this.title = title;
		this.msg = "";
	}

	public void update(int index, String msg) {
		this.index = index;
		this.msg = msg;
	}

	public void error(String msg) {
		this.status = -1;
		this.msg = msg;
	}

	@Override
	public String toString() {
		return "SyncStatus [count=" + count + ", index=" + index + ", msg=" + msg + ", step=" + step + ", status="
				+ status + ", title=" + title + ", tag=" + tag + "]";
	}


}

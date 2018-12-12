package wx.base;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;


import wx.util.date.JsonDateDeserializer;
import wx.util.date.JsonDateSerializer;

public class ProcessStatus {
	public static int STATUS_UNKOWN = 0;
	public static int STATUS_RUNING = 1;
	public static int STATUS_ERROR = -1;
	public static int STATUS_COMPLETE = 2;

	private String name;
	private long count;
	private long index;
	private String message;
	private int status;
	private String user;
	
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
	@JsonSerialize(using = JsonDateSerializer.class)
	@JsonDeserialize(using = JsonDateDeserializer.class)
	private Date startDate;

	@JsonSerialize(using = JsonDateSerializer.class)
	@JsonDeserialize(using = JsonDateDeserializer.class)
	private Date endDate;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public long getIndex() {
		return index;
	}

	public void setIndex(long index) {
		this.index = index;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	
	public void start(long count){
		this.status = STATUS_RUNING;
		this.startDate = new Date();
		this.endDate = null;
		this.index = 0;
		this.count = count; 
	}
	
	public void error(){
		this.status =STATUS_ERROR;
		this.endDate = new Date(); 
	}
	
	public void finish(){
		this.status =STATUS_COMPLETE;
		this.endDate = new Date();
		this.index = this.count;
	}
}

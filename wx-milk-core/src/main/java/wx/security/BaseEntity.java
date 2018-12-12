package wx.security;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import wx.util.date.JsonDateDeserializer;
import wx.util.date.JsonDefaultDateSerializer;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 基础实体类，写实体类，继承此类
 * 
 * @author zhong.h
 *	
 * try it,do it best!
 */
public abstract class BaseEntity<T> implements Serializable, IKey<T>, IEntity {
	
	private static final long serialVersionUID = -4972120468540966454L;

	/** 主键 id */
	private T id;
	
	/** 创建者，因为无论是哪一个用户，都会有创建者和修改者，因此，将这个抽出来，公用 */
	private String createUser;
	
	/** 更新者 ，因为无论是哪一个用户，都会有创建者和修改者，因此，将这个抽出来，公用*/
	private String updateUser;
	
	/** 创建时间 */
	@JsonSerialize(using = JsonDefaultDateSerializer.class) // 日期序列号
	@JsonDeserialize(using = JsonDateDeserializer.class) //
	private Date createTime;
	
	/** 创建时间 */
	@JsonSerialize(using = JsonDefaultDateSerializer.class)
	@JsonDeserialize(using = JsonDateDeserializer.class)
	private Date updateTime;

	@Override
	public T getId() {
		return id;
	}

	@Override
	public void setId(T id) {
		this.id = id;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}

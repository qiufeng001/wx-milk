package wx.milk.model;

import groovy.transform.ToString;
import wx.security.BasicEntity;
import wx.security.IUser;

import java.util.List;

@ToString
public class User extends BasicEntity implements IUser {

	/* 用户姓名 */
	private String name;
	/* 用户密码 */
	private String password;
	/* 用户状态，0-无效，1-有效 */
	private short status;
	/* 账号 */
	private String account;
	/* 备注 */
	private String remark;
	/* 一个用户具有多个角色*/
	private List<Role> roles;

	public String getUserName() {
		return name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public short getStatus() {
		return status;
	}

	public void setStatus(short status) {
		this.status = status;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
}

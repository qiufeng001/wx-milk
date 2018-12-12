package wx.security;

import groovy.transform.ToString;

import java.util.List;

@ToString
public class User extends BasicEntity implements IUser {

	private static final long serialVersionUID = -349249191345535088L;
	
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
	/* 是否在线 */
	private short isOnline;

	@Override
	public String getUserName() {
		return name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public short getStatus() {
		return status;
	}

	public void setStatus(short status) {
		this.status = status;
	}

	@Override
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	@Override
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public short getIsOnline() {
		return isOnline;
	}

	public void setIsOnline(short isOnline) {
		this.isOnline = isOnline;
	}
}

package wx.milk.model;

import wx.security.BasicEntity;

import java.util.List;

public class Role extends BasicEntity {

	private static final long serialVersionUID = 8871041467485135556L;
	
	/* 编号 */
	private String roleNo;

	/* 角色名称 */
	private String name;
	
	/* 编号 */
	private String remark;

	/* 一个角色多个用户 */
	private List<User> users;

	/* 状态 */
	private short status;

	private List<RoleDictions> roleDictions;

	public String getRoleNo() {
		return roleNo;
	}

	public void setRoleNo(String roleNo) {
		this.roleNo = roleNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public short getStatus() {
		return status;
	}

	public void setStatus(short status) {
		this.status = status;
	}

	public List<RoleDictions> getRoleDictions() {
		return roleDictions;
	}

	public void setRoleDictions(List<RoleDictions> roleDictions) {
		this.roleDictions = roleDictions;
	}
}

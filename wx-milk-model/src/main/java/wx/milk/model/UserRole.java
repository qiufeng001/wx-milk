package wx.milk.model;


import com.framework.core.security.BasicEntity;

/**
 * Created by zhong.h on 2018/6/13/013.
 */
public class UserRole extends BasicEntity {

    /* 用户编号 */
    private String account;
    /* 角色编号 */
    private String roleNo;
    /* 状态，0-无效，1-有效 */
    private short status;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getRoleNo() {
        return roleNo;
    }

    public void setRoleNo(String roleNo) {
        this.roleNo = roleNo;
    }

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }
}

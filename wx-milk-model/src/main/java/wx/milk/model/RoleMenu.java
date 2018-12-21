package wx.milk.model;

import com.framework.core.security.BasicEntity;

/**
 *
 * @author zhong.h
 */
public class RoleMenu extends BasicEntity {
    private String roleNo;
    private String menuId;

    public String getRoleNo() {
        return roleNo;
    }

    public void setRoleNo(String roleNo) {
        this.roleNo = roleNo;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }
}

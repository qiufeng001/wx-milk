package wx.milk.model;

import wx.security.BasicEntity;

/**
 * Created by Administrator on 2018/6/21/021.
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
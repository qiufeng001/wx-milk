package wx.milk.service.admin;

import wx.base.service.IService;
import wx.milk.model.UserRole;


public interface IUserRoleService extends IService<UserRole, String> {

    /**
     * 在给菜单分配权限的时候，如果这个角色的菜单权限存在，则不插入
     * @param roleNo
     * @return
     */
    Integer validateUserRole(String roleNo, String accound);
}

package wx.milk.service.admin;
import com.framework.service.IService;
import wx.milk.model.RoleMenu;


public interface IRoleMenuService extends IService<RoleMenu, String> {

    /**
     * 在给菜单分配权限的时候，如果这个角色的菜单权限存在，则不插入
     * @param roleNo
     * @param menuId
     * @return
     */
    Integer validateRoleMenu(String roleNo, String menuId);
}

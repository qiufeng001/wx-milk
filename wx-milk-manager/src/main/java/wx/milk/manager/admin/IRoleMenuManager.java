package wx.milk.manager.admin;

import com.framework.manager.IManager;
import wx.milk.model.RoleMenu;

import java.util.Map;


public interface IRoleMenuManager extends IManager<RoleMenu, String> {

    /**
     * 批量保存菜单权限
     * @param parmas
     * @return
     */
    Integer saveRoleMenu(Map parmas);
}

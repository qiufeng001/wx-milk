package wx.milk.manager.admin;

import com.framework.manager.IManager;
import wx.milk.model.UserRole;

import java.util.Map;


public interface IUserRoleManager extends IManager<UserRole, String> {

    /**
     * 批量保存菜单权限
     * @param parmas
     * @return
     */
    Integer saveUserRole(Map parmas);
}

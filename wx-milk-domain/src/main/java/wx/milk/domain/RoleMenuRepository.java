package wx.milk.domain;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.framework.core.base.domain.IRepository;
import wx.milk.model.RoleMenu;

/**
 * 用户接口
 */
@Mapper
public interface RoleMenuRepository extends IRepository<RoleMenu, String> {

    Integer validateRoleMenu(@Param("roleNo") String roleNo, @Param("menuId") String menuId);

}

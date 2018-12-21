package wx.milk.domain;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.framework.core.base.domain.IRepository;
import wx.milk.model.UserRole;

/**
 * 用户接口
 */
@Mapper
public interface UserRoleRepository extends IRepository<UserRole, String> {

    Integer validateUserRole(@Param("roleNo") String roleNo, @Param("account") String account);
}

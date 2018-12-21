package wx.milk.domain;

import org.apache.ibatis.annotations.Mapper;
import com.framework.core.base.domain.IRepository;
import wx.milk.model.Role;

/**
 * 用户接口
 */
@Mapper
public interface RoleRepository extends IRepository<Role, String> {

}

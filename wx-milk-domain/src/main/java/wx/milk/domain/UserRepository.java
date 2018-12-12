package wx.milk.domain;

import org.apache.ibatis.annotations.Mapper;
import wx.base.domain.IRepository;
import wx.milk.model.Role;
import wx.security.User;


import java.util.List;

/**
 * 用户接口
 */
@Mapper
public interface UserRepository extends IRepository<User, String> {
    /*
     * 根据shiro中的账号用户登录信息，获取用户账号，再用账号获取用户信息
     * @return 用户对象
     */
    User getByAccount(String account);

    /**
     *  通过用户账号获取该账号的权限集合
     *
     * @return 角色集合
     */
    List<Role> listByAccount(String account);

    Integer getOnlineNumber();
}

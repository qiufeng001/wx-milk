package wx.milk.web.configure;

import com.framework.core.query.Query;
import com.framework.core.security.User;
import com.framework.core.util.ControllerUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import wx.milk.model.Dictions;
import wx.milk.model.UserRole;
import wx.milk.service.admin.IDictionsService;
import wx.milk.service.admin.IRoleService;
import wx.milk.service.admin.IUserRoleService;
import wx.milk.service.admin.IUserService;
import wx.milk.web.utils.SpringBeanFactoryUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by zhong.h on 2018/6/13/013.
 */
@Component
public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    private IUserService userService;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IUserRoleService userRoleService;
    @Autowired
    private IDictionsService dictionsService;

    /* 认证.登录 */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        if (userService == null) {
            userService = SpringBeanFactoryUtils.getBean(IUserService.class);
        }
        UsernamePasswordToken userToken = (UsernamePasswordToken) token;//获取用户输入的token
        User user = userService.findByParam(ControllerUtils.getQuery(new Query(), "account",  userToken.getUsername()));
        return new SimpleAuthenticationInfo(user, user.getPassword(),this.getClass().getName());//放入shiro.调用CredentialsMatcher检验密码
    }

    /**
     * 授权用户权限
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(
            PrincipalCollection principals) {



        if (roleService == null) {
            roleService = SpringBeanFactoryUtils.getBean(IRoleService.class);
        }
        //获取用户
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //获取用户角色
        List<UserRole> rolesList = userRoleService.selectByParams(ControllerUtils.getQuery(new Query(), "account",  user.getAccount()));
        Set<String> roleSet = new HashSet<String>();
        for (UserRole userRole: rolesList) {
            roleSet.add(userRole.getRoleNo());
        }
        info.setRoles(roleSet);

        // 获取用户权限
        List<Dictions> dictionsList = dictionsService.selectByParams(ControllerUtils.getQuery(new Query(), "roles",  rolesList));
        Set<String> permissionSet = new HashSet<String>();
        for (Dictions diction: dictionsList) {
            roleSet.add(diction.getName());
        }
        info.setStringPermissions(permissionSet);

        return info;
    }


}

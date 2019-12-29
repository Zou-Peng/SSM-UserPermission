package com.yidong.shiro;

import com.yidong.entity.Permission;
import com.yidong.entity.User;
import com.yidong.service.PermissionService;
import com.yidong.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jnlp.PersistenceService;
import java.util.List;

public class MyRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Autowired
    private PermissionService permissionService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        User user = (User)principalCollection.getPrimaryPrincipal();

        List<Permission> permissionList = permissionService.findUserPermissionByUserId(user.getId());
        SimpleAuthorizationInfo sai = new SimpleAuthorizationInfo();
        if (permissionList != null && permissionList.size()>0){
            for (Permission per : permissionList)
            sai.addStringPermission(per.getPermissionName());
        }
        return sai;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        // 获取用户输入的用户名
        String username = upToken.getUsername();

        // 调用service，查询数据库
        User user = userService.findByUsername(username);

        // 判断：用户名是否存在
        if (user == null) {
            // 认证方法返回NULL，通常都表示用户名错误 UnknownAccountException
            return null;
        }

        /**
         * 执行到这里，用户名是正确。那么，就要校验密码
         * 不用自己校验，shiro自动校验.
         * 原理: 1. 用户名正确； 2. shiro知道用户输入的密码； 3. 需要shiro数据库正确的密码。
         */
        // 参数1：认证后的身份对象。通过subject.getPrincipal()获取这里的参数1
        // 参数2：数据库中正确的密码
        // 参数3：realm名称，可以随意定义。 getName()获取的是默认名称
        SimpleAuthenticationInfo sai =
                new SimpleAuthenticationInfo(user,user.getPassword(),getName());

        return sai;
    }
}

package com.atguigu.shiro.realm;

import com.atguigu.shiro.entity.User;
import com.atguigu.shiro.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MyRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;

    //自定义授权方法:获取当前登录用户的角色、权限信息，返回给shiro用来进行授权认证
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("自定义授权方法");
        //1获取用户身份信息
        String principal = principalCollection.getPrimaryPrincipal().toString();
        //2调用业务层获取用户的角色信息（数据库）
        List<String> roles = userService.getUserRoleInfo(principal);
        System.out.println("当前用户角色信息 = " + roles);
        //2.5调用业务层获取用户的权限信息（数据库）
        List<String> permissions = userService.getUserPermissionInfo(roles);
        System.out.println("当前用户权限信息 = " + permissions);
        //3创建对象，封装当前登录用户的角色、权限信息
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addRoles(roles);
        info.addStringPermissions(permissions);
        //4返回信息
        return info;
    }

    //自定义登录认证方法
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //1获取用户身份信息
        String name = authenticationToken.getPrincipal().toString();
        //2调用业务层获取用户信息（数据库）
        User user = userService.getUserInfoByName(name);
        //3非空判断，将数据封装返回
        if(user !=null){
            AuthenticationInfo info = new SimpleAuthenticationInfo(
                    authenticationToken.getPrincipal(),
                    user.getPwd(),
                    ByteSource.Util.bytes("salt"),
                    authenticationToken.getPrincipal().toString()
            );
            return info;
        }
        return null;
    }
}

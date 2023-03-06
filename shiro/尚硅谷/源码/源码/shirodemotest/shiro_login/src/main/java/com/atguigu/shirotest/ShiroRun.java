package com.atguigu.shirotest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;

public class ShiroRun {
    public static void main(String[] args) {
        //1初始化获取SecurityManager
        IniSecurityManagerFactory factory = new IniSecurityManagerFactory("classpath:shiro.ini");
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        //2获取Subject对象
        Subject subject = SecurityUtils.getSubject();
        //3创建token对象，web应用用户名密码从页面传递
        AuthenticationToken token = new UsernamePasswordToken("zhangsan","z3");
        //4完成登录
        try {
            subject.login(token);
            System.out.println("登录成功");
            //5判断角色
            boolean hasRole = subject.hasRole("role1");
            System.out.println("是否拥有此角色 = " + hasRole);
            //6判断权限
            boolean permitted = subject.isPermitted("user:insert1111");
            System.out.println("是否拥有此权限 = " + permitted);
            //也可以用checkPermission方法，但没有返回值，没权限抛AuthenticationException
            subject.checkPermission("user:select1111");


        }
        catch (UnknownAccountException e) {
            e.printStackTrace();
            System.out.println("用户不存在");

        }
        catch (IncorrectCredentialsException e) {
            e.printStackTrace();
            System.out.println("密码错误");
        }
        catch (AuthenticationException e) {
            e.printStackTrace();

        }

    }
}

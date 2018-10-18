package com.jns.DatabaseRealm;

import com.jns.entity.User;
import com.jns.service.PermissionService;
import com.jns.service.RoleService;
import com.jns.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

public class Realm extends AuthorizingRealm {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //能进入到这里，表示账号已经通过验证了
        String userName =(String) principalCollection.getPrimaryPrincipal();
//通过mapper获取角色和权限
        Set<String> permissions=permissionService.listPermissions(userName);
        Set<String> roles=roleService.listRoles(userName);
        //授权对象
        SimpleAuthorizationInfo s=new SimpleAuthorizationInfo();
        s.setStringPermissions(permissions);
        s.setRoles(roles);
        return s;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //获取账号密码
        UsernamePasswordToken token= (UsernamePasswordToken) authenticationToken;
        String userName=token.getPrincipal().toString();

        User user=userService.findUserByUserName(userName);
        String password=new String(token.getPassword());
        //获取数据库中的密码
        String passwordInDB=user.getUserPassword();

        //第一种方式
        String passwordEncoded=new SimpleHash("md5",password,user.getSalt(),2).toString();

        if(null==passwordInDB||!passwordEncoded.equals(passwordInDB))
            throw new AuthenticationException();
        //认证信息里存放账号密码，getName()是当前Realm的继承方法，返回当前的类名：Realm
        SimpleAuthenticationInfo a=new SimpleAuthenticationInfo(userName,password,getName());

        //第二种方式
        //SimpleAuthenticationInfo a=new SimpleAuthenticationInfo(userName,passwordInDB, ByteSource.Util.bytes(user.getSalt()),getName());
        return a;
    }
}

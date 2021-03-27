package com.hdc.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hdc.User;
import com.hdc.service.RoleService;
import com.hdc.service.UserRoleService;
import com.hdc.service.UserService;
import org.springframework.stereotype.Component;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

/**
 *  自定义realm ，真是的验证和授权都在这
 */
@Component
public class MyRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRoleService userRoleService;

    //根据token判断此Authenticator是否使用该realm
    //必须重写不然shiro会报错
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如@RequiresRoles,@RequiresPermissions之类的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("授权~~~~~");
        String token=principals.toString();
        String username=JWTUtil.getUsername(token);
        //查询用户
        User user = userService.getOne(new QueryWrapper<User>().eq("user_name",username));
        //用户对应的角色Id
        List<Integer> RoleIdlist = userRoleService.selectByUserId(user.getId());
        //角色名称集合
        Set<String> roleNames = roleService.selectByIds(RoleIdlist);
        SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
        info.addRoles(roleNames);
        return info;
    }


    /**
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可，在需要用户认证和鉴权的时候才会调用
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("认证~~~~~~~");
        String jwt= (String) token.getCredentials();
        String username= null;
        //decode时候出错，可能是token的长度和规定好的不一样了
        try {
            username= JWTUtil.getUsername(jwt);
        }catch (Exception e){
            throw new AuthenticationException("token非法，不是规范的token，可能被篡改了，或者过期了");
        }
        if (!JWTUtil.verify(jwt)||username==null){
            throw new AuthenticationException("token认证失效，token错误或者过期，重新登陆");
        }
        User user = userService.getOne(new QueryWrapper<User>().eq("user_name",username));
        if (user==null){
            throw new AuthenticationException("该用户不存在");
        }
        return new SimpleAuthenticationInfo(jwt,jwt,"MyRealm");
    }
}



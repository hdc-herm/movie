package com.hdc.config;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * 实现这个接口是为了，在myrealm类的授权和权限的时候，这个方法的参数是jwt的token的值
 * 否则就是默认的shiro的usernameAndpasswor()方法中的参数的值
 */
public class JWTToken implements AuthenticationToken {

    private String token;

    public JWTToken(String token){
        this.token=token;
    }

    public Object getPrincipal() {
        return token;
    }

    public Object getCredentials() {
        return token;
    }
}


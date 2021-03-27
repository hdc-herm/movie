package com.hdc.config;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * 实现shiro的filter，可以在filter拦截请求的时候，指定这个过滤器，走这个过滤器，
 * 从而用上jwt的token判断请求是否出错或携带
 */
public class JWTFilter extends BasicHttpAuthenticationFilter {

    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        //判断请求的请求头是否带上 "Token"
        if (((HttpServletRequest) request).getHeader("token") != null) {
            try {
                //如果存在，则进入 executeLogin 方法执行登入，检查 token 是否正确
                executeLogin(request, response);
                return true;
            } catch (AuthenticationException e) {
                //token 错误
                responseError(response, e.getMessage());
            }
        }else {
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            try {
                //token为空跳转的请求
                httpServletResponse.sendRedirect("/system/login");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//        }else {
//            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
//            try {
//                //token为空跳转的请求
//                httpServletResponse.sendRedirect("/system/tokenIsNull");
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
        //如果请求头不存在 Token，则可能是执行登陆操作或者是游客状态访问，无需检查 token，直接返回 true
        return true;
    }

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws AuthenticationException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String token = httpServletRequest.getHeader("token");
        JWTToken jwtToken = new JWTToken(token);
        // 提交给realm进行登入，如果错误他会抛出异常并被捕获
        getSubject(request, response).login(jwtToken);//开启验证和授权
        // 如果没有抛出异常则代表登入成功，返回true
        return false;
    }

    /**
     * token错误超时等跳转到 /system/unauthorized/**
     */
    private void responseError(ServletResponse response, String message) {
        try {
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            //设置编码，否则中文字符在重定向时会变为空字符串
            message = URLEncoder.encode(message, "UTF-8");
            httpServletResponse.sendRedirect("/system/unauthorized/" + message);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}


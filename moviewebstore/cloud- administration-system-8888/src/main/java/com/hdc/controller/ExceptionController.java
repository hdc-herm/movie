package com.hdc.controller;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.hdc.Result;
import com.hdc.config.TokenNullException;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

/**
 * 全局异常处理controller
 */
@RestControllerAdvice
public class ExceptionController {
    // 捕捉shiro的异常
    @ExceptionHandler(ShiroException.class)
    public Result handle401(ShiroException e) {
        return new Result(401, e.getMessage(), null);
    }

    // 捕捉shiro的异常
    @ExceptionHandler(UnauthenticatedException.class)
    public Result handle401(UnauthenticatedException e) {
        return new Result(401, "你没有权限访问", null);
    }
    @ExceptionHandler(value = TokenExpiredException.class)
    public Result handler(TokenExpiredException e) throws IOException {
        return new Result(HttpStatus.BAD_REQUEST.value(),"token已经过期，请重新登录",null);
    }

    @ExceptionHandler(value = Exception.class)
    public Result handler(Exception e) throws IOException {
        return new Result(444,e.getMessage());
    }
}

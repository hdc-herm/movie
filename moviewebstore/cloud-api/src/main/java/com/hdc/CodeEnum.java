package com.hdc;

import lombok.AllArgsConstructor;
import lombok.Data;

public enum CodeEnum {
    loginError(10001,"用户名或密码错误"),
    loginNull(10002,"用户名获取失败！"),
    loginUserNull(10003,"用户不存在，请先注册"),
    loginUserFrozen(10005,"该用户以被冻结！"),
    loginSuccess(0,"登录成功"),
    registerError(10004,"用户名已存在"),
    registerSuccess(0,"注册成功");

    private int code;
    private String message;

    CodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

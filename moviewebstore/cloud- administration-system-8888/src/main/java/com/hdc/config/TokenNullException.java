package com.hdc.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenNullException extends Exception {
    private int code;  //异常状态码
    private String message;  //异常信息
    private String descinfo;   //描述
}

package com.hdc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hdc.CodeEnum;
import com.hdc.User;

import javax.servlet.http.HttpServletRequest;

public interface UserService extends IService<User> {
    CodeEnum selectByUser(User user);

    CodeEnum userRegister(User user);
}

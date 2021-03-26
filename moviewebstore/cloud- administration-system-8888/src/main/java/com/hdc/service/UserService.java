package com.hdc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hdc.Result;
import com.hdc.User;

public interface UserService extends IService<User> {

    Result find(User user);

}

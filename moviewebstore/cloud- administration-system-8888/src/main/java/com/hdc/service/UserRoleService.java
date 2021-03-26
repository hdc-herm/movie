package com.hdc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hdc.UserRole;

import java.util.List;

public interface UserRoleService extends IService<UserRole> {
    List<Long> selectByUserId(Long userId);
}

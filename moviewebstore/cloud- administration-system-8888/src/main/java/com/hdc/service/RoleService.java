package com.hdc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hdc.Role;

import java.util.List;
import java.util.Set;

public interface RoleService extends IService<Role> {

    Set<String> selectByIds(List<Long> roleIdlist);
}

package com.hdc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hdc.Role;
import com.hdc.dao.RoleDao;
import com.hdc.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleDao, Role> implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Override
    public Set<String> selectByIds(List<Long> roleIdlist) {
        List<Role> roles = roleDao.selectList(new QueryWrapper<Role>().in("id", roleIdlist));
        Set<String> collect = roles.stream().map(Role::getRoleName).collect(Collectors.toSet());
        return collect;
    }
}

package com.hdc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hdc.UserRole;
import com.hdc.dao.UserRoleDao;
import com.hdc.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleDao, UserRole> implements UserRoleService {

    @Autowired
    private UserRoleDao userRoleDao;

    public List<Integer> selectByUserId(int userId) {
        List<UserRole> userRoles = userRoleDao.selectList(new QueryWrapper<UserRole>().eq("user_id", userId));
        if (!CollectionUtils.isEmpty(userRoles)){
            List<Integer> roleIds = userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toList());
            return roleIds;
        }
        return null;
    }

}

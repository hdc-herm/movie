package com.hdc.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hdc.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao extends BaseMapper<User> {
}

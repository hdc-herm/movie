package com.hdc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hdc.Result;
import com.hdc.User;
import com.hdc.config.JWTUtil;
import com.hdc.dao.UserDao;
import com.hdc.service.UserService;
import jdk.nashorn.internal.parser.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.List;


@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

    @Autowired
    private UserDao userDao;

    public Result find(User user) {
        User loginUser = userDao.selectOne(new QueryWrapper<User>().eq("user_name", user.getUserName()));
        if (loginUser != null) {
            if (user.getPassword().equals(user.getPassword())){
                String token = null;
                try {
                    token = JWTUtil.createToken(user);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return new Result(200,"登录成功!",token);
            }else {
                return new Result(300,"密码错误!");
            }
        }else {
            return new Result(400,"用户名不存在!");
        }

    }

    @Override
    public List<User> selectUserS() {
        List<User> users = userDao.selectList(new QueryWrapper<User>());
        return users;
    }
}

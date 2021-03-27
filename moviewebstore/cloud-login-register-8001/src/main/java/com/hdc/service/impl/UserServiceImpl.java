package com.hdc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hdc.CodeEnum;
import com.hdc.User;
import com.hdc.dao.UserDao;
import com.hdc.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class UserServiceImpl extends ServiceImpl<UserDao,User> implements UserService {

    @Autowired
    private UserDao userDao;

    public CodeEnum selectByUser(User user) {
        //如果用户名为空,返回获取用户名失败
        if (StringUtils.isEmpty(user.getUserName())){
            return CodeEnum.loginNull;
        }else {
            User user1 = userDao.selectOne(new QueryWrapper<User>().eq("user_name", user.getUserName()));
            //根据用户名查询用户，失败返回该用户不存在
            if (user1 == null){
                return CodeEnum.loginUserNull;
            }else if (user1.getStatus() == 1) {
                return CodeEnum.loginUserFrozen;
            }else if (user1.getPassword().equals(user.getPassword()) ){
                //用户名密码正确成功登录
                return CodeEnum.loginSuccess;
            }else {
                //如果用户存在，密码错误，返回密码错误
                return CodeEnum.loginError;
            }
        }
    }

    public CodeEnum userRegister(User user) {
        User user1 = userDao.selectOne(new QueryWrapper<User>().eq("user_name", user.getUserName()));
        if (user1 != null) {
            return CodeEnum.registerError;
        }else {
           userDao.insert(user);
           return CodeEnum.registerSuccess;
        }
    }

    @Override
    public User selectByName(String username) {
        User user = userDao.selectOne(new QueryWrapper<User>().eq("user_name", username));
        return user;
    }

    @Override
    public int updateUser(User user) {
        int i = userDao.updateById(user);
        return i;
    }
}

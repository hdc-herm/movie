package com.hdc.controller;

import com.hdc.Result;
import com.hdc.User;
import com.hdc.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("system")
public class SystemController {

    @Autowired
    private UserService userServicel;


    @GetMapping("/logout")
    public void logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
    }

    /**
     *  用户登录，登录成功返回token
     * @param user
     * @return
     */
    @PostMapping("/login")
    public Result login(User user) {
        Result result = userServicel.find(user);
        return result;
    }

    /**
     * 后台管理系统首页
     * @return
     */
    @RequiresRoles("admin")
    @GetMapping("/index")
    public Result index(){
        return new Result(200,"这是后台系统首页");
    }

    /**
     * token错误返回信息
     * @param message
     * @return
     */
    @GetMapping("/unauthorized/{message}")
    public Result unauthorized(@PathVariable("message") String message){
        return new Result(3003,message);
    }

    @GetMapping("/tokenIsNull")
    public Result tokenIsNull(){
        return new Result(3005,"请先登录，token为空！");
    }


}

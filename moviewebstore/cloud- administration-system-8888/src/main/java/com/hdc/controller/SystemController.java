package com.hdc.controller;

import com.hdc.Result;
import com.hdc.User;
import com.hdc.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
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
    @PostMapping("/signing")
    public String login(User user, Model model) {
        Result result = userServicel.find(user);
        if (result.getCode() == 200) {
            return "index";
        }
        model.addAttribute("error",result.getMessage());
        return "login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * 后台管理系统首页
     * @return
     */
//    @RequiresRoles("admin")
    @GetMapping("/index")
    public String index(Model model){
        List<User> users = userServicel.selectUserS();
        model.addAttribute("userList",users);
        return "index";
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

}

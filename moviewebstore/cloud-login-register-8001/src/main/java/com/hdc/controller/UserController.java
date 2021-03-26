package com.hdc.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.hdc.CodeEnum;
import com.hdc.Result;
import com.hdc.User;
import com.hdc.dao.UserDao;
import com.hdc.openfeign.MovieFeign;
import com.hdc.rabbitmq.*;
import com.hdc.service.UserService;
import com.hdc.util.JWTUtil;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDao userDao;

    /**
     * movie电影服务 远程调用实列
     */
    @Autowired
    private MovieFeign movieFeign;
    /**
     * 登录接口
     * @param user 登录传入的用户信息
     * @return 登录成功返回token
     */
    @PostMapping("/signing")
    public String login(User user, Model model,HttpServletRequest httpServletRequest){
        CodeEnum codeEnum = userService.selectByUser(user);
        //登录成功返回token
        if (codeEnum.getCode() == 0){
            String token = null;
            try {
               token = JWTUtil.createToken(user);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            httpServletRequest.getSession().setAttribute("token",token);
            return "index";
        }else {
            model.addAttribute("error",codeEnum.getMessage());
            return "login";
        }
    }

    /**
     * 跳转注册页面
     * @return
     */
    @GetMapping("/regist")
    public String register(){
        return "regist";
    }

    /**
     * 注册接口
     * @param user 传入注册的信息
     * @return 成功返回 注册成功
     */
    @PostMapping("/saveUser")
    public String register(User user,Model model){
        CodeEnum codeEnum = userService.userRegister(user);
        if (codeEnum.getCode() == 0){
            return "login";
        }else {
            model.addAttribute("error",codeEnum.getMessage());
            return "regist";
        }
    }

    /**
     * 跳转登录页面
     * @return
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * 跳转首页
     * @return
     */
    @GetMapping("/index")
    public String index(){
        return "index";
    }

    /**
     * 分页查询电影信息
     * @param num
     * @param size
     * @return
     */
    @GetMapping("/moviePage")
    @ResponseBody
    public Result moviePage(int num,int size) {
        Result result = movieFeign.moviePage(num, size);
        return result;
    }

    @GetMapping("/testMq")
    public String testMq(String name){
        RabbitmqFactory rabbitmqFactory = new RabbitmqFactory("127.0.0.1",5672,"hdc","123","ems");
        RabbitTemplate rabbitTemplate = rabbitmqFactory.getConnection();
        MqBinding mqBinding = new MqBinding();
        mqBinding.binding1();
        //发送消息
        rabbitTemplate.convertAndSend(QueueConstant.QUEUE_01,name);
        return "login";
    }
}

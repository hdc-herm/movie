package com.hdc.controller;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hdc.CodeEnum;
import com.hdc.Movie;
import com.hdc.Result;
import com.hdc.User;
import com.hdc.dao.UserDao;
import com.hdc.openfeign.MovieFeign;
import com.hdc.rabbitmq.*;
import com.hdc.service.UserService;
import com.hdc.util.JWTUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.apache.commons.lang.StringUtils;
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
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * movie电影服务 远程调用实列
     */
    @Autowired
    private MovieFeign movieFeign;

    /**
     * 登录接口
     *
     * @param user 登录传入的用户信息
     * @return 登录成功返回token
     */
    @PostMapping("/signing")
    public String login(User user, Model model, HttpServletRequest httpServletRequest) {
        CodeEnum codeEnum = userService.selectByUser(user);
        //登录成功返回token
        if (codeEnum.getCode() == 0) {
            String token = null;
            try {
                token = JWTUtil.createToken(user);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            httpServletRequest.getSession().setAttribute("token", token);
            return "index";
        } else {
            model.addAttribute("error", codeEnum.getMessage());
            return "login";
        }
    }

    /**
     * 跳转注册页面
     *
     * @return
     */
    @GetMapping("/regist")
    public String register() {
        return "regist";
    }

    /**
     * 注册接口
     *
     * @param user 传入注册的信息
     * @return 成功返回 注册成功
     */
    @PostMapping("/saveUser")
    public String register(User user, Model model) {
        CodeEnum codeEnum = userService.userRegister(user);
        if (codeEnum.getCode() == 0) {
            return "login";
        } else {
            model.addAttribute("error", codeEnum.getMessage());
            return "regist";
        }
    }

    /**
     * 跳转登录页面
     *
     * @return
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * 跳转首页
     *
     * @return
     */
    @GetMapping("/index")
    public String index() {
        return "index";
    }

    /**
     * 分页查询电影信息
     *
     * @param num
     * @param size
     * @return
     */
    @GetMapping("/moviePage")
    @ResponseBody
    public Result moviePage(int num, int size) {
        Result result = movieFeign.moviePage(num, size);
        return result;
    }

    /**
     * 电影加入收藏
     * @param mvId
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("/addCollect")
    public String addCollect(String mvId, Model model,HttpServletRequest request) {
        HttpSession session = request.getSession();
        String token = (String) session.getAttribute("token");
        if (StringUtils.isEmpty(token)) {
            return "login";
        }
        String username = JWTUtil.getUsername(token);
        User user = userService.selectByName(username);
        movieFeign.insertLike(Integer.parseInt(mvId), user.getId());
        //todo 添加个人收藏电影
        System.out.println("添加个人收藏电影成功！");
        //todo 查询当前用户收藏列表第一页 返回收藏列表
        Result result = movieFeign.myLikeMovie(user.getId(), 1);
        model.addAttribute("page", result.getData());
        System.out.println(JSON.toJSONString(model));
        return "collectList";
    }


    /**
     * 分页展示电影信息
     * @param type
     * @param page
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/moviePageType")
    public String moviePageType(String type, int page, Model model) throws Exception {
//        String utfType = (String) movieType.get(type);
        Result result = movieFeign.moviePageType(type, page);
//        PageInfo<Movie> pageInfo = (PageInfo<Movie>) result.getData();
        System.out.println(JSON.toJSONString(result.getData()));
        model.addAttribute("page", result.getData());
        model.addAttribute("type", type);
        System.out.println(JSON.toJSONString(model));
        return "movieList";
    }

    /**
     * 我的收藏
     * @param page
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("/getCollect")
    public String myLikeMovie(int page, Model model,HttpServletRequest request){
        HttpSession session = request.getSession();
        String token = (String) session.getAttribute("token");
        if (StringUtils.isEmpty(token)) {
            return "login";
        }
        String username = JWTUtil.getUsername(token);
        User user = userService.selectByName(username);
        Result result = movieFeign.myLikeMovie(user.getId(), page);

        model.addAttribute("page", result.getData());
        return "collectList";
    }


    /**
     * 根据电影名称查询电影
     * @param mvName
     * @param model
     * @return
     */
    @GetMapping("/selectByName")
    public String selectByName(String mvName,Model model){
        Result result = movieFeign.selectByName(mvName);
        model.addAttribute("movies", result.getData());
        model.addAttribute("key", mvName);
        return "search";
    }

    /**
     * 根据电影ID查询电影信息
     *
     * @param id
     * @return
     */
    @GetMapping("/movieSelectById")
    public String movieSelectById(int id, Model model) {
        Result result = movieFeign.movieSelectById(id);
        model.addAttribute("movie", result.getData());
        return "movieInfo";
    }

    /**
     * 服务降级演示接口
     * @return
     */
    @GetMapping("/find")
    @ResponseBody
    public String find(){
        return movieFeign.find();
    }

    /**
     * 个人中心
     * @param model
     * @param request
     * @return
     */
    @GetMapping("/userInfo")
    public String getUser(Model model,HttpServletRequest request) {
        User u = new User();
        HttpSession session = request.getSession();
        String token = (String) session.getAttribute("token");
        if (StringUtils.isEmpty(token)) {
            return "login";
        }
        String username = JWTUtil.getUsername(token);
        User user = userService.selectByName(username);
        //todo 查询用户信息
        model.addAttribute("user", user);
        return "userInfo";
    }

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    @RequestMapping("/updateUser")
    public String updateUser(User user) {
        //todo 更新用户信息
        userService.updateUser(user);
        return "index";
    }

//    @GetMapping("/testMq")
//    public String testMq(String name){
//        RabbitmqFactory rabbitmqFactory = new RabbitmqFactory("127.0.0.1",5672,"hdc","123","ems");
//        RabbitTemplate rabbitTemplate = rabbitmqFactory.getConnection();
//        MqBinding mqBinding = new MqBinding();
//        mqBinding.binding1();
//        //发送消息
//        rabbitTemplate.convertAndSend(QueueConstant.QUEUE_01,name);
//        return "login";
//    }
}

package com.hdc.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.hdc.Movie;
import com.hdc.Result;
import com.hdc.dao.MovieDao;
import com.hdc.service.MovieService;
import com.hdc.service.UserMovieService;
import com.hdc.util.JWTUtil;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/movie")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private UserMovieService userMovieService;

    /**
     * 分页查询电影信息
     *
     * @param num
     * @param size
     * @return
     */
    @GetMapping("/moviePage")
//    @HystrixCommand
    public Result moviePage(int num, int size) {
        List<Movie> movies = movieService.moviePage(num, size);

        return new Result().ok(movies);
    }

    /**
     * 分类分页查询
     *
     * @param mvType
     * @param num
     * @return
     */
    @GetMapping("/moviePageType")
//    @HystrixCommand
    public Result moviePageType(String mvType, int num) {
        PageHelper.startPage(num, 10);  //myatis分页插件   limit (page-1)*30+1,30
        List<Movie> movies = movieService.moviePageType(mvType);
        PageInfo<Movie> pageInfo = new PageInfo<Movie>(movies);//page 包含分页所有信息  当前页  所有记录数   总页数 等
        return new Result().ok(pageInfo);
    }

    /**
     * 根据类型查询电影
     *
     * @param mvType
     * @return
     */
    @GetMapping("/movieType")
//    @HystrixCommand
    public Result movieType(String mvType) {
        Result result = movieService.movieType(mvType);
        return result;
    }

    /**
     * 根据电影ID查询电影信息
     *
     * @param id
     * @return
     */
    @GetMapping("/movieSelectById")
//    @HystrixCommand
    public Result movieSelectById(int id) {
        Movie movie = movieService.movieSelectById(id);
        return new Result().ok(movie);
    }

    /**
     * 添加到想看
     * @param mvId
     * @param userId
     * @return
     */
    @GetMapping("/insertLike")
//    @HystrixCommand
    public Result insertLike(int mvId,int userId) {
        Result result = userMovieService.insertData(mvId, userId);
        return result;
    }

    /**
     * 根据名称模糊查询电影
     * @param mvName
     * @return
     */
    @GetMapping("/selectByName")
    public Result selectByName(String mvName) {
        List<Movie> movies = movieService.selectByName(mvName);
        return new Result().ok(movies);
    }


    /**
     * 服务降级演示接口
     * @return
     */
    @GetMapping("/find")
    @HystrixCommand(fallbackMethod = "findFallback")
    public String find(){
        int i = 10/0;
        return "";
    }


    /**
     * 个人搜藏列表
     * @param userId
     * @param page
     * @return
     */
    @GetMapping("/myLikeMovie")
    public Result myLikeMovie(int userId , int page){
        PageHelper.startPage(page, 10);  //myatis分页插件   limit (page-1)*30+1,30
        List<Movie> movies = movieService.myLikeMovie(userId);
        PageInfo<Movie> pageInfo = new PageInfo<Movie>(movies);//page 包含分页所有信息  当前页  所有记录数   总页数 等
        return new Result().ok(pageInfo);
    }

    public String findFallback(){
        return "服务降级指定方法返回的友好提示";
    }

    public String defaultFallbackMethod(){
        return "服务降级通用方法返回的友好提示";
    }

}

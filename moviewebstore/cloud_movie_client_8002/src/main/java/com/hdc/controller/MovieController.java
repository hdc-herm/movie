package com.hdc.controller;

import com.alibaba.fastjson.JSON;
import com.hdc.Movie;
import com.hdc.Result;
import com.hdc.dao.MovieDao;
import com.hdc.service.MovieService;
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

    /**
     *分页查询电影信息
     * @param num
     * @param size
     * @return
     */
    @GetMapping("/moviePage")
    public Result moviePage(int num,int size){
        List<Movie> movies = movieService.moviePage(num, size);

        return new Result().ok(movies);
    }

    /**
     * 分类分页查询
     * @param mvType
     * @param num
     * @param size
     * @return
     */
    @GetMapping("/moviePageType")
    public Result moviePageType(String mvType,int num,int size){
        if (size == 0) {
            size = 20;
        }
        List<Movie> movies = movieService.moviePageType(mvType,num, size);
        return new Result().ok(movies);
    }

    /**
     * 根据类型查询电影
     * @param mvType
     * @return
     */
    @GetMapping("/movieType")
    public Result movieType(String mvType){
        Result result = movieService.movieType(mvType);
        return result;
    }

    /**
     * 根据电影ID查询电影信息
     * @param id
     * @return
     */
    @GetMapping("/movieSelectById")
    public Result movieSelectById(int id){
        Movie movie = movieService.movieSelectById(id);
        return new Result().ok(movie);
    }

    @GetMapping

    public Result insertLike(){}

}

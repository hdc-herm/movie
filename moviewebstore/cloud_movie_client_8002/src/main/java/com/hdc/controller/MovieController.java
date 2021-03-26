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

    @Autowired
    private MovieDao movieDao;


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
}

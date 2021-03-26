package com.hdc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hdc.Movie;
import com.hdc.dao.MovieDao;
import com.hdc.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieServiceImpl extends ServiceImpl<MovieDao, Movie> implements MovieService {

    @Autowired
    private MovieDao movieDao;

    public List<Movie> moviePage(int num, int size) {
        num = (num-1)*size;
        List<Movie> movies = movieDao.moviePage(num, size);
        return movies;
    }
}

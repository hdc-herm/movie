package com.hdc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hdc.Movie;
import com.hdc.Result;
import com.hdc.UserMovie;
import com.hdc.dao.MovieDao;
import com.hdc.dao.UserMovieDao;
import com.hdc.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl extends ServiceImpl<MovieDao, Movie> implements MovieService {

    @Autowired
    private MovieDao movieDao;

    @Autowired
    private UserMovieDao userMovieDao;

    public List<Movie> moviePage(int num, int size) {
        num = (num-1)*size;
        List<Movie> movies = movieDao.moviePage(num, size);
        return movies;
    }

    @Override
    public Result movieType(String mvType) {
        List<Movie> movies = movieDao.selectList(new QueryWrapper<Movie>().like("mv_type", mvType));
        return new Result().ok(movies);
    }

    @Override
    public List<Movie> moviePageType(String mvType) {
        List<Movie> movies = movieDao.moviePageType(mvType);
        return movies;
    }

    @Override
    public Movie movieSelectById(int id) {
        Movie movie = movieDao.selectOne(new QueryWrapper<Movie>().eq("id", id));
        return movie;
    }

    @Override
    public List<Movie> selectByName(String mvName) {
        List<Movie> movies = movieDao.selectList(new QueryWrapper<Movie>().like("mv_name", mvName).eq("mv_status", 0));
        return movies;
    }

    @Override
    public List<Movie> myLikeMovie(int userId) {
        List<UserMovie> userMovies = userMovieDao.selectList(new QueryWrapper<UserMovie>().eq("user_id", userId));
        List<Integer> mvIds = userMovies.stream().map(UserMovie::getMvId).collect(Collectors.toList());
        List<Movie> movies = movieDao.selectList(new QueryWrapper<Movie>().in("id", mvIds));
        return movies;
    }
}

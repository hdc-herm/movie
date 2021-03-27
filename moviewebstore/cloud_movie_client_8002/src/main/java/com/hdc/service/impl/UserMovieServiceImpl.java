package com.hdc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hdc.Result;
import com.hdc.UserMovie;
import com.hdc.dao.UserMovieDao;
import com.hdc.service.UserMovieService;
import com.hdc.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class UserMovieServiceImpl extends ServiceImpl<UserMovieDao, UserMovie> implements UserMovieService {

    @Autowired
    private UserMovieDao userMovieDao;

    @Override
    public Result insertData(int mvId, int userId) {
        List<UserMovie> userMovies = userMovieDao.selectList(new QueryWrapper<UserMovie>().eq("mv_id", mvId).eq("user_id", userId));
        if (CollectionUtils.isEmpty(userMovies)) {
            UserMovie userMovie = new UserMovie();
            userMovie.setMvId(mvId);
            userMovie.setUserId(userId);
            userMovie.setStartTime(new Date());
            userMovieDao.insert(userMovie);
            return new Result().ok(null);
        }else {
            return new Result().ok(null);
        }
    }
}

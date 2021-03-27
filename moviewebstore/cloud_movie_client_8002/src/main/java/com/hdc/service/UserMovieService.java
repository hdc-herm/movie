package com.hdc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hdc.Result;
import com.hdc.UserMovie;

import javax.servlet.http.HttpServletRequest;

public interface UserMovieService extends IService<UserMovie> {

    Result insertData(int mvId, int userId);

}

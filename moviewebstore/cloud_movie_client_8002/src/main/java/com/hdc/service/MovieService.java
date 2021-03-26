package com.hdc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hdc.Movie;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MovieService extends IService<Movie> {

    List<Movie> moviePage(int num,int size);

}

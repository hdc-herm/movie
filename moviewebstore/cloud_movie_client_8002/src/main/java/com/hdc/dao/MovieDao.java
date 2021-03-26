package com.hdc.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hdc.Movie;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MovieDao extends BaseMapper<Movie> {

    List<Movie> moviePage(@Param("num") int num, @Param("size") int size);

}

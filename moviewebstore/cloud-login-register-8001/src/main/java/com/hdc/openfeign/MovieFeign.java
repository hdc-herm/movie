package com.hdc.openfeign;

import com.hdc.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "cloud-movie-client")
public interface MovieFeign {

    /**
     * 远程调用分页查询电影方法
     * @param num 当前页
     * @param size 每页显示的数据
     * @return 查询的电影的集合
     */
    @GetMapping("/movie/moviePage")
    public Result moviePage(@RequestParam("num") int num,@RequestParam("size") int size);

}

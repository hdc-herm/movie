package com.hdc.openfeign;

import com.hdc.Result;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.apache.ibatis.annotations.Param;
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


    @GetMapping("/movie/insertLike")
    public Result insertLike(@RequestParam("mvId") int mvId,@RequestParam("userId") int userId);

    @GetMapping("/movie/moviePageType")
    public Result moviePageType(@RequestParam("mvType") String mvType,@RequestParam("num") int num);

    @GetMapping("/movie/selectByName")
    public Result selectByName(@RequestParam("mvName") String mvName);

    @GetMapping("/movie/movieSelectById")
    public Result movieSelectById(@RequestParam("id") int id);

    @GetMapping("/movie/find")
    public String find();

    @GetMapping("/movie/myLikeMovie")
    public Result myLikeMovie(@RequestParam("userId") int userId ,@RequestParam("page") int page);
}

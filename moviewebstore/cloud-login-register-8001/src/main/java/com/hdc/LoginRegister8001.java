package com.hdc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
//开启openfeign支持
@EnableFeignClients
//开启服务降级
@EnableHystrix
public class LoginRegister8001 {

    public static void main(String[] args) {

        SpringApplication.run(LoginRegister8001.class, args);
    }

}

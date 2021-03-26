package com.hdc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
//开启服务熔断
@EnableCircuitBreaker
public class MovieClient8002 {

    public static void main(String[] args) {

        SpringApplication.run(MovieClient8002.class,args);

    }

}

//package com.hdc.filter;
//
//import com.alibaba.fastjson.JSON;
//import com.hdc.Result;
//import com.hdc.util.JWTUtil;
//import org.apache.commons.lang.StringUtils;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.core.Ordered;
//import org.springframework.core.io.buffer.DataBuffer;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.http.server.reactive.ServerHttpResponse;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//import reactor.netty.http.server.HttpServerRequest;
//
///**
// * gateway全局过滤器 实现globalfilter接口即可，无需其他配置
// * ordered接口是配置多个filter时，getOrder方法返回值值越小越先执行
// */
//@Component
//public class LoginCheckFilter implements GlobalFilter, Ordered {
//
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        //获取request
//        ServerHttpRequest request = exchange.getRequest();
//        ServerHttpResponse response = exchange.getResponse();
//        //请求地址
//        String path = request.getURI().getPath();
//        //登录请求不拦截
//        if ("/user/login".equals(path) || "/user/signing".equals(path)){
//            return chain.filter(exchange);
//        }
//        if (path.equals("/user/index")){
//            String url = "http://localhost/user/login";
//            //303状态码表示由于请求对应的资源存在着另一个URI，应使用GET方法定向获取请求的资源
//            response.setStatusCode(HttpStatus.SEE_OTHER);
//            response.getHeaders().set(HttpHeaders.LOCATION, url);
//            return response.setComplete();
//        }
//        HttpHeaders headers = request.getHeaders();
//        String token = headers.getFirst("token");
//        //获取response
////        ServerHttpResponse response = exchange.getResponse();
//        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
//        //token判断不过返回的数据结构
//        Result result = new Result();
//        //如果token为空，请求打回
//        if (StringUtils.isEmpty(token)){
////            Result error = result.error(401, "当前尚未登录，请登录");
////            String message = JSON.toJSONString(error);
////            DataBuffer buffer = response.bufferFactory().wrap(message.getBytes());
////            return response.writeWith(Mono.just(buffer));
//            String url = "http://想跳转的网址";
//            //303状态码表示由于请求对应的资源存在着另一个URI，应使用GET方法定向获取请求的资源
//            response.setStatusCode(HttpStatus.SEE_OTHER);
//            response.getHeaders().set(HttpHeaders.LOCATION, url);
//            return response.setComplete();
//        }
//        //如果token不为空，但是校验失败，超时，错误等
//        if (StringUtils.isNotEmpty(token)){
//            boolean verify = JWTUtil.verify(token);
//            if (verify){
//                chain.filter(exchange);
//            }else {
//                Result error = result.error(402, "token失效，请重新登录");
//                String message = JSON.toJSONString(error);
//                DataBuffer buffer = response.bufferFactory().wrap(message.getBytes());
//                return response.writeWith(Mono.just(buffer));
//            }
//        }
//        return chain.filter(exchange);
//    }
//
//    public int getOrder() {
//        return 0;
//    }
//}

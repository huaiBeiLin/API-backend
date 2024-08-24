package com.yuxin.yuapigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class YuapiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(YuapiGatewayApplication.class, args);
    }

//    @Bean
//    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
//        return builder.routes()
//                .route("tobaidu", r -> r.path("/baidu")
//                        .uri("http://www.baidu.com"))
//                .route("tosanjin", r -> r.path("/sanjin")
//                        .uri("http://101.37.17.235"))
//                .build();
//    }

}

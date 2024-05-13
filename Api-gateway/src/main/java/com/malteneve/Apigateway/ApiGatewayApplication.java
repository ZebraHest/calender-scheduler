package com.malteneve.Apigateway;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/event/all")
                        .filters(f -> f
                                .addResponseHeader("X-Powered-By", "Gateway Service")
                                .stripPrefix(1))
                        .uri("http://localhost:8082"))
                .route(r -> r.path("/event/add")
                        .filters(f -> f
                                .addResponseHeader("X-Powered-By", "Gateway Service")
                                .stripPrefix(1))
                        .uri("http://localhost:8082"))
                .route(r -> r.path("/user/**")
                        .filters(f -> f
                                .addResponseHeader("X-Powered-By", "Gateway Service")
                                .stripPrefix(1))
                        .uri("http://localhost:8081"))
                .build();
    }

}

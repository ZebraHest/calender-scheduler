package com.malteneve.Apigateway;


import com.malteneve.Apigateway.config.AuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ApiGatewayApplication {

    @Autowired
    AuthFilter authFilter;

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {

        AuthFilter.Config config = AuthFilter.Config.builder()
                .build();

        GatewayFilter filter = authFilter.apply(config);

        return builder.routes()
                .route(r -> r.path("/event/**")
                        .filters(f -> f
                                .filter(filter)
                                .stripPrefix(1))
                        .uri("http://localhost:8082"))

                .route(r -> r.path("/user/**")
                        .filters(f -> f
                                .stripPrefix(1))
                        .uri("http://localhost:8081"))

                .route(r -> r.path("/scheduler/**")
                        .filters(f -> f
                                .filter(filter)
                                .stripPrefix(1))
                        .uri("http://localhost:8083"))
                .build();
    }

}

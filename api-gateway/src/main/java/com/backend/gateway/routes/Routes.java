package com.backend.gateway.routes;

import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

@Configuration
public class Routes {

    @Bean
    public RouterFunction<ServerResponse> userServiceRoute() {
        return GatewayRouterFunctions.route("user_service")
                .route(RequestPredicates.path("/api/user/**")
                        .or(RequestPredicates.path("/api/auth/**")), HandlerFunctions.http("http://localhost:8080"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> postServiceRoute() {
        return GatewayRouterFunctions.route("post_service")
                .route(RequestPredicates.path("/api/post"), HandlerFunctions.http("http://localhost:8081"))
                .build();
    }
}
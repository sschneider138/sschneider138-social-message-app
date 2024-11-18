package com.backend.gateway.routes;

import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;

import java.net.URI;

import org.springframework.cloud.gateway.server.mvc.filter.CircuitBreakerFilterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
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
        .filter(CircuitBreakerFilterFunctions.circuitBreaker("userServiceCircuitBreaker",
            URI.create("forward:/fallbackRoute")))
        .build();
  }

  @Bean
  public RouterFunction<ServerResponse> postServiceRoute() {
    return GatewayRouterFunctions.route("post_service")
        .route(RequestPredicates.path("/api/post/**"), HandlerFunctions.http("http://localhost:8081"))
        .filter(CircuitBreakerFilterFunctions.circuitBreaker("postServiceCircuitBreaker",
            URI.create("forward:/fallbackRoute")))
        .build();
  }

  @Bean
  public RouterFunction<ServerResponse> likeServiceRoute() {
    return GatewayRouterFunctions.route("like_service")
        .route(RequestPredicates.path("/api/like/**"), HandlerFunctions.http("http://localhost:8082"))
        .filter(CircuitBreakerFilterFunctions.circuitBreaker("likeServiceCircuitBreaker",
            URI.create("forward:/fallbackRoute")))
        .build();
  }

  @Bean
  public RouterFunction<ServerResponse> emailServiceRoute() {
    return GatewayRouterFunctions.route("email_service")
        .route(RequestPredicates.path("/api/email/**"), HandlerFunctions.http("http://localhost:8082"))
        .filter(CircuitBreakerFilterFunctions.circuitBreaker("emailServiceCircuitBreaker",
            URI.create("forward:/fallbackRoute")))
        .build();
  }

  @Bean
  public RouterFunction<ServerResponse> fallbackRoute() {
    return route("fallbackRoute")
        .GET("/fallbackRoute", request -> ServerResponse.status(HttpStatus.SERVICE_UNAVAILABLE)
            .body("service unavailable. please try again later"))
        .build();
  }

}
package com.backend.post.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI postServiceApi() {
        return new OpenAPI()
                .info(new Info().title("Post Service API")
                .description( "REST API documentation for social media messaging application's Post microservice")
                .version("v0.0.1"));
    }

}

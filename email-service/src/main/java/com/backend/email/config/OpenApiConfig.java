package com.backend.email.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

  @Bean
  public OpenAPI mailServiceApi() {
    return new OpenAPI()
        .info(new Info().title("Email Service API")
            .description("REST API documentation for social media messaging application's email microservice")
            .version("v0.0.1"));
  }
}

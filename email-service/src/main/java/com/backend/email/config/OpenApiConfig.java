package com.backend.email.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

  @Bean
  public OpenAPI mailServiceApi() {
    return new OpenAPI()
        .info(new Info().title("Mail Service API")
            .description("REST API documentation for social media messaging application's Mail microservice")
            .version("v0.0.1"));
  }
}

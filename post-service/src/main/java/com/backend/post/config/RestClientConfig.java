package com.backend.post.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import com.backend.post.client.UserClient;

@Configuration
public class RestClientConfig {

  @Value("${user.url}")
  private String userServiceUrl;

  @Bean
  public UserClient userClient() {
    RestClient restClient = RestClient.builder()
        .baseUrl(userServiceUrl)
        .build();

    var restClientAdapter = RestClientAdapter.create(restClient);
    var httpServiceProxyFactory = HttpServiceProxyFactory.builderFor(restClientAdapter).build();
    return httpServiceProxyFactory.createClient(UserClient.class);
  }
}

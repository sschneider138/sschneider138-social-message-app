package com.backend.post.config;

import com.backend.post.external.client.LikeClient;
import com.backend.post.external.client.UserClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.ClientHttpRequestFactories;
import org.springframework.boot.web.client.ClientHttpRequestFactorySettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.time.Duration;

@Configuration
public class RestClientConfig {

  @Value("${user.service.url}")
  private String userServiceUrl;

  @Value("${like.service.url}")
  private String likeServiceUrl;

  @Bean
  public UserClient userClient() {
    RestClient restClient = RestClient.builder()
        .baseUrl(userServiceUrl)
        .requestFactory(getClientRequestFactory())
        .build();

    var restClientAdapter = RestClientAdapter.create(restClient);
    var httpServiceProxyFactory = HttpServiceProxyFactory.builderFor(restClientAdapter).build();
    return httpServiceProxyFactory.createClient(UserClient.class);
  }


  @Bean
  public LikeClient likeClient() {
    RestClient restClient = RestClient.builder()
        .baseUrl(likeServiceUrl)
        .requestFactory(getClientRequestFactory())
        .build();

    var restClientAdapter = RestClientAdapter.create(restClient);
    var httpServiceProxyFactory = HttpServiceProxyFactory.builderFor(restClientAdapter).build();
    return httpServiceProxyFactory.createClient(LikeClient.class);
  }


  private ClientHttpRequestFactory getClientRequestFactory() {
    ClientHttpRequestFactorySettings clientHttpRequestFactorySettings = ClientHttpRequestFactorySettings.DEFAULTS
        .withConnectTimeout(Duration.ofSeconds(3))
        .withReadTimeout(Duration.ofSeconds(3));

    return ClientHttpRequestFactories.get(clientHttpRequestFactorySettings);
  }
}

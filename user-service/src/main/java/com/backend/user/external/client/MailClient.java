package com.backend.user.external.client;

import com.backend.user.external.dto.MailDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;

public interface MailClient {

  Logger log = LoggerFactory.getLogger(MailClient.class);

  @PostExchange("/api/email/send")
  @CircuitBreaker(name = "email", fallbackMethod = "fallbackMethod")
  @Retry(name = "email")
  void sendEmail(@Valid @RequestBody MailDto mailDto);

  default void fallbackMethod(@Valid @RequestBody MailDto mailDto, Throwable throwable) {
    log.error("cannot send email to address: {}\nfailure reason: {}\n", mailDto.recipient(), throwable.getMessage());
  }
}

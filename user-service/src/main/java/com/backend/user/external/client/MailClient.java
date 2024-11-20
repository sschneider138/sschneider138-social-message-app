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

  @PostExchange("/api/mail/send")
  @CircuitBreaker(name = "mail", fallbackMethod = "fallbackMethod")
  @Retry(name = "mail")
  void sendEmail(@Valid @RequestBody MailDto mailDto);

  default void fallbackMethod(@Valid @RequestBody MailDto mailDto, Throwable throwable) {
    log.error("cannot send mail to address: {}\nfailure reason: {}\n", mailDto.recipient(), throwable.getMessage());
  }
}
 
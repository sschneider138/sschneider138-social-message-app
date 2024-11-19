package com.backend.user.service;

import com.backend.user.client.MailClient;
import com.backend.user.dto.MailDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class MailService {

  private final MailClient mailClient;

  @Async
  public void sendAsyncEmail(@Valid @RequestBody MailDto mailDto) {
    try {
      mailClient.sendEmail(mailDto);
    } catch (Exception e) {
      throw new RuntimeException("unable to send confirmation email: " + e.getMessage());
    }
  }
}

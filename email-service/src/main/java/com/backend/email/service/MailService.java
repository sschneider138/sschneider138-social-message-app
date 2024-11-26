package com.backend.email.service;

import java.util.Date;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.backend.email.dto.MailDto;
import com.backend.email.model.Mail;
import com.backend.email.repository.MailRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MailService {

  private final JavaMailSender mailSender;
  private final MailRepository mailRepository;

  public void sendEmail(@Valid MailDto mailDto) {
    SimpleMailMessage message = new SimpleMailMessage();

    try {
      Mail email = Mail.builder()
          .recipient(mailDto.recipient())
          .subject(mailDto.subject())
          .body(mailDto.body())
          .build();

      mailRepository.save(email);

      message.setFrom("social-messaging-app@email.com");
      message.setTo(email.getRecipient());
      message.setSubject(email.getSubject());
      message.setText(email.getBody());
      message.setSentDate(Date.from(email.getDateSent()));

      mailSender.send(message);
    } catch (Exception e) {
      throw new RuntimeException("error sending email: " + e.getMessage(), e);
    }
  }
}

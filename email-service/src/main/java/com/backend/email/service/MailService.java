package com.backend.email.service;

import com.backend.email.dto.MailDto;
import com.backend.email.model.Mail;
import com.backend.email.repository.MailRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class MailService {

  private final JavaMailSender mailSender;
  private final MailRepository mailRepository;

  public void sendEmail(@Valid MailDto mailDto) {
    SimpleMailMessage message = new SimpleMailMessage();

    try {
      Mail mail = Mail.builder()
          .recipient(mailDto.recipient())
          .subject(mailDto.subject())
          .body(mailDto.body())
          .build();

      mailRepository.save(mail);

      message.setFrom("social-messaging-app@email.com");
      message.setTo(mail.getRecipient());
      message.setSubject(mail.getSubject());
      message.setText(mail.getBody());
      message.setSentDate(Date.from(mail.getDateSent()));

      mailSender.send(message);
    } catch (Exception e) {
      throw new RuntimeException("error sending mail: " + e.getMessage(), e);
    }
  }
}

package com.backend.email.controller;

import com.backend.email.dto.MailDto;
import com.backend.email.service.MailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/email")
@RequiredArgsConstructor
public class MailController {

  private final MailService mailService;

  @PostMapping("/send")
  public ResponseEntity<String> sendEmail(@Valid @RequestBody MailDto mailDto) {
    mailService.sendEmail(mailDto);
    return ResponseEntity.ok("email sent successfully");
  }
}

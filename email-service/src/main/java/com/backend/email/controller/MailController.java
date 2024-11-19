package com.backend.email.controller;

import com.backend.email.dto.MailDto;
import com.backend.email.service.MailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/api/mail")
@RequiredArgsConstructor
public class MailController {

  private final MailService mailService;

  @PostMapping("/send")
  @ResponseStatus(HttpStatus.OK)
  public HashMap<String, String> sendEmail(@Valid @RequestBody MailDto mailDto) {
    HashMap<String, String> body = new HashMap<>();
    
    mailService.sendEmail(mailDto);
    body.put("status", "email sent successfully");
    return body;
  }
}

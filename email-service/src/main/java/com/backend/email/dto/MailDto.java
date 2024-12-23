package com.backend.email.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record MailDto(
    @NotBlank(message = "recipient is required")
    @Email 
    String recipient,

    @NotBlank(message = "subject is required")
    String subject,

    @NotBlank(message = "body is required") 
    String body) {
}

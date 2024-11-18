package com.backend.email.dto;

import jakarta.validation.constraints.NotBlank;

public record MailDto(
    @NotBlank(message = "uuid is required")
    String emailUUID,

    @NotBlank(message = "recipient is required")
    String recipient,

    @NotBlank(message = "subject is required")
    String subject,

    @NotBlank(message = "body is required")
    String body
) {
}

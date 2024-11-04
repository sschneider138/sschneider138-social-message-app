package com.messaging.app.backend.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserAuthenticationDto(
        @NotBlank(message = "password cannot be blank") String password,

        @NotBlank(message = "email is required") @Email(message = "please enter a valid email") String email
) {
}

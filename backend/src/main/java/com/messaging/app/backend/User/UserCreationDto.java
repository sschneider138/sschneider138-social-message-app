package com.messaging.app.backend.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;

public record UserCreationDto(
        @NotBlank(message = "first name is required") String firstName,

        @NotBlank(message = "last name is required") String lastName,

        @NotBlank(message = "username is required") @Size(min = 3, max = 15, message = "username must be between 3 and 15 characters") String username,

        @NotBlank(message = "email is required") @Email(message = "please enter a valid email") String email,

        @Pattern(regexp = "^[+]?[0-9]{10,15}$", message = "Phone number should be valid and can include country code") String phoneNumber,

        @Size(max = 5, message = "You may select up to 5 top interests") List<String> topInterests) {
}

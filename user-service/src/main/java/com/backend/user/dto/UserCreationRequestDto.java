package com.backend.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;

public record UserCreationRequestDto(
        @NotBlank(message = "first name is required")
        String firstName,

        @NotBlank(message = "last name is required")
        String lastName,

        @NotBlank(message = "username is required")
        String username,

        @NotBlank(message = "email is required")
        @Email(message = "please enter a valid email")
        String email,

        @NotBlank(message = "phone number is required")
        @Pattern(regexp = "^[+]?[0-9]{10,15}$", message = "phone number should be valid and can include country code")
        String phoneNumber,

        @NotBlank(message = "password cannot be blank")
        // password validation occurs in service layer since pw is encoded
        // validation here would check against encoded pw, not raw pw
        String password,

        @Size(max = 5, message = "you may select up to 5 top interests")
        List<String> topInterests) {
}

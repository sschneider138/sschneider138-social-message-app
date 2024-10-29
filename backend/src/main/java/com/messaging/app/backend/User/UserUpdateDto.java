package com.messaging.app.backend.User;

import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserUpdateDto(
    @Size(min = 3, max = 15, message = "Username must be between 3 and 15 characters") String username,
    @Email(message = "Please enter a valid email") String email,
    @Pattern(regexp = "^[+]?[0-9]{10,15}$", message = "Phone number should be valid and can include country code") String phoneNumber,
    @Size(max = 5, message = "You may select up to 5 top interests") List<String> topInterests) {
}

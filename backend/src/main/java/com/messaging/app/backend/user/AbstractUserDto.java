package com.messaging.app.backend.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public abstract class AbstractUserDto {

    @NotBlank(message = "first name is required")
    private final String firstName;

    @NotBlank(message = "last name is required")
    private final String lastName;

    @NotBlank(message = "username is required")
    @Size(min = 3, max = 15, message = "username must be between 3 and 15 characters")
    private final String username;

    @NotBlank(message = "email is required")
    @Email(message = "please enter a valid email")
    private final String email;

    @Pattern(regexp = "^[+]?[0-9]{10,15}$", message = "Phone number should be valid and can include country code")
    private final String phoneNumber;

    @NotBlank(message = "password cannot be blank")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$",
            message = "Password must be at least 8 characters and include at least one uppercase letter, one lowercase letter, one digit, and one special character"
    )
    private final String password;

    @Size(max = 5, message = "You may select up to 5 top interests")
    private final List<String> topInterests;
}

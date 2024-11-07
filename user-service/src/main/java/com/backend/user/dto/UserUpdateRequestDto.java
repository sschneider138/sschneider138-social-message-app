package com.backend.user.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

// used for client -> server transfer
public record UserUpdateRequestDto(

        String firstName,

        String lastName,

        @NotBlank(message = "you must provide the username of the account you wish to update")
        String username,

        String email,

        String phoneNumber,

        @NotBlank(message = "you must provide the password of the account you wish to update")
        String password,

        List<String> topInterests) {
}







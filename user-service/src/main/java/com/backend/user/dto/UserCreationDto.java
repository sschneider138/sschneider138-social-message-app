package com.backend.user.dto;

import java.util.List;

public class UserCreationDto extends AbstractUserDto {

    public UserCreationDto(String firstName, String lastName, String username, String email, String phoneNumber, String password, List<String> topInterests) {
        super(firstName, lastName, username, email, phoneNumber, password, topInterests);
    }
}
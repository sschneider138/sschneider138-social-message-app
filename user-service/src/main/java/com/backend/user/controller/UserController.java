package com.backend.user.controller;

import com.backend.user.dto.*;
import com.backend.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<UserResponseDto> getAllUsers() {
        return userService.getAllUsers();
    }


    @GetMapping("/all/paginated")
    @ResponseStatus(HttpStatus.OK)
    public PageDto<UserResponseDto> getAllPaginatedUsers(@RequestBody PaginationRequestDto paginationRequestDto) {
        int currentPage = Math.max(paginationRequestDto.pageIndex(), 0);
        int currentLimit = (paginationRequestDto.itemsPerPage() > 0) ? paginationRequestDto.itemsPerPage() : 10;
        return userService.getAllPaginatedUsers(currentPage, currentLimit);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDto createNewUser(@RequestBody UserCreationRequestDto userCreationRequestDto) {
        return userService.createUser(userCreationRequestDto);
    }


    @GetMapping("/get/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponseDto getIndividualUser(@PathVariable UUID uuid) {
        return userService.getByUUID(uuid);
    }


    @PatchMapping("/update/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponseDto partialUpdateIndividualUser(
            @PathVariable UUID uuid, @Valid @RequestBody UserUpdateRequestDto userUpdateRequestDto) {
        return userService.partialUpdateIndividualUser(uuid, userUpdateRequestDto);
    }
}

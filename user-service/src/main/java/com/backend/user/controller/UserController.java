package com.backend.user.controller;

import com.backend.user.dto.PageDto;
import com.backend.user.dto.PaginationRequestDto;
import com.backend.user.dto.UserResponseDto;
import com.backend.user.dto.UserUpdateDto;
import com.backend.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("all")
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

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponseDto getIndividualUser(@PathVariable Long id) {
        return userService.getIndividualUser(id);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponseDto partialUpdateIndividualUser(
            @PathVariable Long id,
            @Valid @RequestBody UserUpdateDto userUpdateDto) {
        return userService.partialUpdateIndividualUser(id, userUpdateDto);
    }
}

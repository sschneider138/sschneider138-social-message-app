package com.messaging.app.backend.User;

import com.messaging.app.backend.Pagination.UserPaginationRequestDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("all")
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        var allUsers = userService.getAllUsers();
        return ResponseEntity.ok(allUsers);
    }

    @GetMapping("/all/paginated")
    public ResponseEntity<UserPageDto> getAllPaginatedUsers(
            @RequestBody UserPaginationRequestDto userPaginationRequestDto) {
        var currentPage = Math.max(userPaginationRequestDto.pageIndex(), 0);
        var currentLimit = (userPaginationRequestDto.itemsPerPage() > 0) ? userPaginationRequestDto.itemsPerPage() : 10;
        var userPageDto = userService.getAllPaginatedUsers(currentPage, currentLimit);
        return ResponseEntity.ok(userPageDto);
    }

    @PostMapping("/new")
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserCreateDto userCreateDto) {
        UserResponseDto savedUser = userService.createUser(userCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getIndividualUser(@PathVariable Long id) {
        UserResponseDto user = userService.getIndividualUser(id);
        return ResponseEntity.ok(user);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserResponseDto> partialUpdateIndividualUser(@PathVariable Long id,
                                                                       @Valid @RequestBody UserUpdateDto userUpdateDto) {
        UserResponseDto updatedUser = userService.partialUpdateIndividualUser(id, userUpdateDto);
        return ResponseEntity.ok(updatedUser);
    }
}

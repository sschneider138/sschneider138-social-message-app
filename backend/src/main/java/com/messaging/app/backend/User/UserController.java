package com.messaging.app.backend.User;

import com.messaging.app.backend.Pagination.PageDto;
import com.messaging.app.backend.Pagination.PaginationRequestDto;
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
        List<UserResponseDto> allUsers = userService.getAllUsers();
        return ResponseEntity.ok(allUsers);
    }

    @GetMapping("/all/paginated")
    public ResponseEntity<PageDto<UserResponseDto>> getAllPaginatedUsers(
            @RequestBody PaginationRequestDto paginationRequestDto) {
        int currentPage = Math.max(paginationRequestDto.pageIndex(), 0);
        int currentLimit = (paginationRequestDto.itemsPerPage() > 0) ? paginationRequestDto.itemsPerPage() : 10;
        PageDto<UserResponseDto> pageDto = userService.getAllPaginatedUsers(currentPage, currentLimit);
        return ResponseEntity.ok(pageDto);
    }

    @PostMapping("/new")
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserCreationDto userCreationDto) {
        UserResponseDto savedUser = userService.createUser(userCreationDto);
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

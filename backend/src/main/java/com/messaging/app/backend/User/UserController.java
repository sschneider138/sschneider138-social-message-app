package com.messaging.app.backend.User;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.messaging.app.backend.Pagination.UserPaginationRequestDto;

import jakarta.validation.Valid;

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
    var currentPage = (userPaginationRequestDto.pageIndex() >= 0) ? userPaginationRequestDto.pageIndex() : 0;
    var currentLimit = (userPaginationRequestDto.itemsPerPage() > 0) ? userPaginationRequestDto.itemsPerPage() : 10;
    var userPageDto = userService.getAllPaginatedUsers(currentPage, currentLimit);
    return ResponseEntity.ok(userPageDto);
  }

  @PostMapping("/new")
  public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserCreateDto userCreateDto) {
    var savedUser = userService.createUser(userCreateDto);
    var responseDto = new UserResponseDto(savedUser.getFirstName(), savedUser.getLastName(), savedUser.getUsername(),
        savedUser.getTopInterests(), savedUser.getDateJoined());
    return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<UserResponseDto> partialUpdateUser(@PathVariable Long id,
      @Valid @RequestBody UserUpdateDto userUpdateDto) {
    User updatedUser = userService.partialUpdateUser(id, userUpdateDto);
    var responseDto = new UserResponseDto(
        updatedUser.getFirstName(), updatedUser.getLastName(), updatedUser.getUsername(),
        updatedUser.getTopInterests(), updatedUser.getDateJoined());
    return ResponseEntity.ok(responseDto);
  }
}

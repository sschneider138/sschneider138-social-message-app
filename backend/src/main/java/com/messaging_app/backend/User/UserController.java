package com.messaging_app.backend.User;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
  public UserPageDto getAllPaginatedUsers(@RequestParam int pageIndex, @RequestParam int itemsPerPage) {
    var currentPage = (pageIndex >= 0) ? pageIndex : 0;
    var currentLimit = (itemsPerPage > 0) ? itemsPerPage : 10;
    return userService.getAllPaginatedUsers(currentPage, currentLimit);
  }

  @PostMapping("/new")
  public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserCreateDto userCreateDto) {
    var savedUser = userService.createUser(userCreateDto);
    var responseDto = new UserResponseDto(savedUser.getFirstName(), savedUser.getLastName(), savedUser.getUsername(),
        savedUser.getTopInterests(), savedUser.getDateJoined());
    URI location = ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(savedUser.getId())
        .toUri();
    return ResponseEntity.created(location).body(responseDto);
  }

}

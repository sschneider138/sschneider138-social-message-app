package com.messaging.app.backend.User;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.messaging.app.backend.Pagination.UserPaginationRequestDto;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("test")
  public Map<String, String> test() {
    var status = new HashMap<String, String>();
    status.put("hi", "there");
    return status;
  }

  @GetMapping("all")
  public ResponseEntity<List<UserResponseDto>> getAllUsers() {
    var allUsers = userService.getAllUsers();
    return ResponseEntity.ok(allUsers);

  }

  @GetMapping("/all/paginated")
  public UserPageDto getAllPaginatedUsers(@RequestBody UserPaginationRequestDto userPaginationRequestDto) {
    var currentPage = (userPaginationRequestDto.pageIndex() >= 0) ? userPaginationRequestDto.pageIndex() : 0;
    var currentLimit = (userPaginationRequestDto.itemsPerPage() > 0) ? userPaginationRequestDto.itemsPerPage() : 10;
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
    System.out.println("user created");
    return ResponseEntity.created(location).body(responseDto);
  }

}

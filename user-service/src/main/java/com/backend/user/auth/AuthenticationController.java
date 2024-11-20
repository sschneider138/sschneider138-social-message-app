package com.backend.user.auth;

import com.backend.user.dto.UserAuthenticationDto;
import com.backend.user.dto.UserCreationRequestDto;
import com.backend.user.dto.UserResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthenticationService authenticationService;

  @PostMapping("/register")
  @ResponseStatus(HttpStatus.CREATED)
  public AuthenticationResponse register(@Valid @RequestBody UserCreationRequestDto userCreationRequestDto) {
    return authenticationService.register(userCreationRequestDto);
  }

  @PostMapping("/authenticate")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public AuthenticationResponse authenticate(@Valid @RequestBody UserAuthenticationDto userAuthenticationDto) {
    return authenticationService.authenticate(userAuthenticationDto);
  }

  @GetMapping("/validate")
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<UserResponseDto> validateUser(@RequestHeader("Authorization") String token) {
    return ResponseEntity.ok(authenticationService.validateAndGetUser(token));
  }

}
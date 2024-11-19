package com.backend.user.auth;

import com.backend.user.config.JwtService;
import com.backend.user.dto.UserAuthenticationDto;
import com.backend.user.dto.UserCreationRequestDto;
import com.backend.user.dto.UserResponseDto;
import com.backend.user.model.User;
import com.backend.user.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthenticationService authenticationService;
  private final UserRepository userRepository;
  private final JwtService jwtService;

  @PostMapping("/register")
  @ResponseStatus(HttpStatus.CREATED)
  public AuthenticationResponse register(@Valid @RequestBody UserCreationRequestDto userCreationRequestDto) {
    return authenticationService.register(userCreationRequestDto);
  }

  @PostMapping("/authenticate")
  @ResponseStatus(HttpStatus.OK)
  public AuthenticationResponse authenticate(@Valid @RequestBody UserAuthenticationDto userAuthenticationDto) {
    return authenticationService.authenticate(userAuthenticationDto);
  }

  @GetMapping("/validate")
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<UserResponseDto> validateUser(@RequestHeader("Authorization") String token) {
    // remove "bearer " prefix if present
    String jwt = token.startsWith("Bearer ") ? token.substring(7) : token;

    // extract the username from the jwt token
    String username = jwtService.extractUsername(jwt);

    Optional<User> userOpt = userRepository.findByUsername(username);
    if (userOpt.isEmpty() || jwtService.isTokenExpired(jwt)) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    User user = userOpt.get();
    UserResponseDto userResponseDto = new UserResponseDto(
        user.getUserUUID(),
        user.getUsername(),
        user.getLastName(),
        user.getUsername(),
        user.getEmail(),
        user.getPhoneNumber(),
        user.getTopInterests(),
        user.getDateJoined(),
        user.getMembershipLength());

    return ResponseEntity.ok(userResponseDto);
  }

}
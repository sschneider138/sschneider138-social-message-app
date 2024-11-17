package com.backend.user.auth;

import com.backend.user.config.JwtService;
import com.backend.user.dto.UserAuthenticationDto;
import com.backend.user.dto.UserCreationRequestDto;
import com.backend.user.model.User;
import com.backend.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final UserRepository repository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;


  @Transactional
  public AuthenticationResponse register(@Valid UserCreationRequestDto userDto) {

    User user = User.builder()
        .firstName(userDto.firstName())
        .lastName(userDto.lastName())
        .username(userDto.username())
        .email(userDto.email())
        .phoneNumber(userDto.phoneNumber())
        .password(passwordEncoder.encode(userDto.password()))
        .topInterests(userDto.topInterests())
        .build();
    repository.save(user);

    String jwtToken = jwtService.generateToken(user);
    return AuthenticationResponse.builder().token(jwtToken).build();
  }

  public AuthenticationResponse authenticate(@Valid UserAuthenticationDto userAuthenticationDto) {
    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
        userAuthenticationDto.email(), userAuthenticationDto.password()));

    User user = repository.findByEmail(userAuthenticationDto.email()).orElseThrow();
    String jwtToken = jwtService.generateToken(user);
    return AuthenticationResponse.builder().token(jwtToken).build();
  }

  private boolean isPasswordValid(String password) {
    // password must be 8 characters and include at least one uppercase letter,
    // one lowercase letter, one digit, and one special character
    return password != null
        && password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$");
  }

}

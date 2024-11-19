package com.backend.user.auth;

import com.backend.user.config.JwtService;
import com.backend.user.dto.MailDto;
import com.backend.user.dto.UserAuthenticationDto;
import com.backend.user.dto.UserCreationRequestDto;
import com.backend.user.model.User;
import com.backend.user.repository.UserRepository;
import com.backend.user.service.MailService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final UserRepository repository;
  private final MailService mailService;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;


  @Transactional
  public AuthenticationResponse register(UserCreationRequestDto userDto) {

    if (!isPasswordValid(userDto.password())) {
      throw new RuntimeException("password must be 8 characters and include at least one uppercase letter, one lowercase letter, one digit, and one special character");
    }

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

    MailDto mailDto = new MailDto(
        userDto.email(),
        "Welcome to Our Site!",
        "Thank you for registering " + userDto.username() + ". We are so excited for the great things we will do together. As part of our community, you are part of a world changing movement."
    );

    mailService.sendAsyncEmail(mailDto);

    String jwtToken = jwtService.generateToken(user);
    return AuthenticationResponse.builder().token(jwtToken).build();
  }

  public AuthenticationResponse authenticate(UserAuthenticationDto userAuthenticationDto) {
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

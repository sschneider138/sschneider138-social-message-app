package com.backend.user.auth;

import com.backend.user.config.JwtService;
import com.backend.user.dto.UserAuthenticationDto;
import com.backend.user.dto.UserCreationRequestDto;
import com.backend.user.dto.UserResponseDto;
import com.backend.user.external.dto.MailDto;
import com.backend.user.model.User;
import com.backend.user.repository.UserRepository;
import com.backend.user.service.MailService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final UserRepository userRepository;
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
    userRepository.save(user);

    String body = String.format("""
        Thank you for registering %s!
        
        We are so excited for the great things we will do together. As part of our community, you are part of a world changing movement.
        
        Best regards,
        Social Messaging App
        
        """, userDto.username());

    MailDto mailDto = new MailDto(
        userDto.email(),
        "Welcome to Our Site!",
        body
    );

    mailService.sendAsyncEmail(mailDto);

    String jwtToken = jwtService.generateToken(user);
    return AuthenticationResponse.builder().token(jwtToken).build();
  }

  public AuthenticationResponse authenticate(UserAuthenticationDto userAuthenticationDto) {
    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
        userAuthenticationDto.email(), userAuthenticationDto.password()));

    User user = userRepository.findByEmail(userAuthenticationDto.email()).orElseThrow();
    String jwtToken = jwtService.generateToken(user);
    return AuthenticationResponse.builder().token(jwtToken).build();
  }


  public UserResponseDto validateAndGetUser(@RequestHeader("Authorization") String token) {
    String jwt = extractJWT(token);

    String username = jwtService.extractUsername(jwt);

    User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("invalid user"));

    if (jwtService.isTokenExpired(jwt)) {
      throw new RuntimeException("token expired");
    }

    return new UserResponseDto(
        user.getUserUUID(),
        user.getUsername(),
        user.getLastName(),
        user.getUsername(),
        user.getEmail(),
        user.getPhoneNumber(),
        user.getTopInterests(),
        user.getDateJoined(),
        user.getMembershipLength());
  }

  private boolean isPasswordValid(String password) {
    // password must be 8 characters and include at least one uppercase letter,
    // one lowercase letter, one digit, and one special character
    return password != null
        && password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$");
  }

  private String extractJWT(String token) {
    return token.startsWith("Bearer ") ? token.substring(7) : token;
  }

}

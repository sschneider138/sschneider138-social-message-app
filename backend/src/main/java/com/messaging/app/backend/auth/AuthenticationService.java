package com.messaging.app.backend.auth;


import com.messaging.app.backend.config.JwtService;
import com.messaging.app.backend.user.User;
import com.messaging.app.backend.user.UserAuthenticationDto;
import com.messaging.app.backend.user.UserCreationDto;
import com.messaging.app.backend.user.UserRepository;
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
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public AuthenticationResponse register(UserCreationDto userDto) {
        User user = User.builder()
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .username(userDto.getUsername())
                .email(userDto.getEmail())
                .phoneNumber(userDto.getPhoneNumber())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .topInterests(userDto.getTopInterests())
                .build();
        repository.save(user);

        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public AuthenticationResponse authenticate(UserAuthenticationDto userAuthenticationDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userAuthenticationDto.email(), userAuthenticationDto.password()));

        User user = repository.findByEmail(userAuthenticationDto.email()).orElseThrow();
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }
}


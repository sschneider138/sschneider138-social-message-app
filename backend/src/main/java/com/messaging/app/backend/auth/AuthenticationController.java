package com.messaging.app.backend.auth;

import com.messaging.app.backend.user.UserAuthenticationDto;
import com.messaging.app.backend.user.UserCreationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody UserCreationDto userCreationDto) {
        return ResponseEntity.ok(service.register(userCreationDto));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody UserAuthenticationDto userAuthenticationDto) {
        return ResponseEntity.ok(service.authenticate(userAuthenticationDto));
    }
}
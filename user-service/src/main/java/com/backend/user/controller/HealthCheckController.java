package com.backend.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/health")
public class HealthCheckController {

    @GetMapping("/check")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, String> unsecuredHealthEndpoint() {
        HashMap<String, String> status = new HashMap<>();
        status.put("status", "ok");
        status.put("secureEndpoint", "false");
        return status;
    }

    @GetMapping("/secure/check")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, String> securedHealthEndpoint() {
        HashMap<String, String> status = new HashMap<>();
        status.put("status", "ok");
        status.put("secureEndpoint", "true");
        return status;
    }

}

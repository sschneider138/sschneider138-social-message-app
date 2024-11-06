package com.backend.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/health-check")
public class HealthCheckController {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Map<String, String> HealthCheck() {
        HashMap<String, String> status = new HashMap<>();
        status.put("status", "ok");
        return status;
    }

}

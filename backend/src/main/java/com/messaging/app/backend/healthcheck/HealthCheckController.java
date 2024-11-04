package com.messaging.app.backend.healthcheck;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/health-check")
public class HealthCheckController {

    @GetMapping
    public Map<String, String> HealthCheck() {
        var status = new HashMap<String, String>();
        status.put("status", "ok");
        return status;
    }

}

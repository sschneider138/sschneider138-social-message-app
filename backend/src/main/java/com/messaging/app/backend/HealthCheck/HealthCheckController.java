package com.messaging.app.backend.HealthCheck;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class HealthCheckController {

  @GetMapping("/check")
  public Map<String, String> HealthCheck() {
    var status = new HashMap<String, String>();
    status.put("status", "ok");
    return status;
  }

}

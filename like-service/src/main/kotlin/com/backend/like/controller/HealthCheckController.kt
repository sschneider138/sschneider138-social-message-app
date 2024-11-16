package com.backend.like.controller

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/health")
class HealthCheckController {

    @GetMapping("/check")
    @ResponseStatus(HttpStatus.OK)
    fun healthcheck(): Map<String, String> {
        var map = mutableMapOf<String, String>()
        map["status"] = "ok"
        return map
    }
}
package com.hbb.ratelimiter.controller;

import com.hbb.ratelimiter.annotation.RateLimit;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class DemoController {

    /**
     * Rate-limited endpoint: 5 requests per 10 seconds.
     * Hit 6 times quickly → 6th returns HTTP 429.
     */
    @GetMapping("/api/v1/test")
    @RateLimit(key = "demo-api", capacity = 5, refillTokens = 5, refillPeriodSeconds = 10)
    public Map<String, Object> test() {
        return Map.of(
                "status", "success",
                "message", "Request allowed ✅",
                "timestamp", System.currentTimeMillis()
        );
    }

    @GetMapping("/api/v1/health")
    public Map<String, String> health() {
        return Map.of("status", "UP");
    }
}


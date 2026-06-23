package com.hbb.ratelimiter.controller;

import com.hbb.ratelimiter.annotation.RateLimit;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class DemoController {

    /**
     * Allows 5 requests per 10 seconds.
     * Try hitting this 6 times in quick succession → 6th request returns 429.
     */
    @GetMapping("/test")
    @RateLimit(key = "demo-api", capacity = 5, refillTokens = 5, refillPeriodSeconds = 10)
    public Map<String, Object> test() {
        return Map.of(
                "status", "success",
                "message", "Request allowed ✅",
                "timestamp", System.currentTimeMillis()
        );
    }

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of("status", "UP");
    }
}

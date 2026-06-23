package com.hbb.ratelimiter.controller;

import com.hbb.ratelimiter.annotation.RateLimit;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class DemoController {

    @GetMapping("/")
    public Map<String, Object> welcome() {
        return Map.of(
                "app", "Distributed Rate Limiter",
                "author", "Vanshika",
                "status", "🟢 Live",
                "description", "Production-grade distributed rate limiter using Token Bucket algorithm with Redis + Spring Boot AOP.",
                "endpoints", List.of(
                        Map.of("path", "/api/v1/health", "description", "Health check"),
                        Map.of("path", "/api/v1/test", "description", "Rate-limited endpoint (5 req / 10 sec)")
                ),
                "tip", "Hit /api/v1/test 6 times quickly to see HTTP 429 🔥",
                "github", "https://github.com/Vanshikash26/Project-4"
        );
    }

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

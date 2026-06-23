package com.hbb.ratelimiter.aspect;

import com.hbb.ratelimiter.annotation.RateLimit;
import com.hbb.ratelimiter.service.RateLimiterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * AOP aspect that intercepts methods annotated with @RateLimit.
 * If rate limit exceeded → returns HTTP 429.
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class RateLimitAspect {

    private final RateLimiterService rateLimiterService;

    @Around("@annotation(rateLimit)")
    public Object enforceRateLimit(ProceedingJoinPoint joinPoint, RateLimit rateLimit) throws Throwable {
        boolean allowed = rateLimiterService.isAllowed(
                rateLimit.key(),
                rateLimit.capacity(),
                rateLimit.refillTokens(),
                rateLimit.refillPeriodSeconds()
        );

        if (!allowed) {
            log.warn("Rate limit exceeded for key={}", rateLimit.key());
            return ResponseEntity
                    .status(HttpStatus.TOO_MANY_REQUESTS)
                    .header("Retry-After", String.valueOf(rateLimit.refillPeriodSeconds()))
                    .body(Map.of(
                            "error", "Too Many Requests",
                            "message", "Rate limit exceeded. Try again later.",
                            "retryAfterSeconds", rateLimit.refillPeriodSeconds()
                    ));
        }
        return joinPoint.proceed();
    }
}

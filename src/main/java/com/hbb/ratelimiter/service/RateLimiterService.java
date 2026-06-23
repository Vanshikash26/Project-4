package com.hbb.ratelimiter.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * Core service that talks to Redis and runs the Lua-based Token Bucket algorithm.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RateLimiterService {

    private final StringRedisTemplate redisTemplate;
    private final DefaultRedisScript<Long> rateLimitScript;

    public boolean isAllowed(String key, int capacity, int refillTokens, int refillPeriodSeconds) {
        String redisKey = "rate_limit:" + key;
        Long result = redisTemplate.execute(
                rateLimitScript,
                Collections.singletonList(redisKey),
                String.valueOf(capacity),
                String.valueOf(refillTokens),
                String.valueOf(refillPeriodSeconds),
                String.valueOf(System.currentTimeMillis())
        );
        boolean allowed = result != null && result == 1L;
        log.debug("RateLimit check key={} allowed={}", redisKey, allowed);
        return allowed;
    }
}

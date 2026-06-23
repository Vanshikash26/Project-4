package com.hbb.ratelimiter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

@Configuration
public class RedisConfig {

    @Bean
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory factory) {
        return new StringRedisTemplate(factory);
    }

    /**
     * Lua script for atomic Token Bucket algorithm.
     * KEYS[1] = bucket key
     * ARGV[1] = capacity, ARGV[2] = refillTokens, ARGV[3] = refillPeriodSeconds, ARGV[4] = nowMillis
     * Returns 1 if request allowed, 0 if rate-limited.
     */
    @Bean
    public DefaultRedisScript<Long> rateLimitScript() {
        DefaultRedisScript<Long> script = new DefaultRedisScript<>();
        script.setScriptText(
            "local key = KEYS[1] " +
            "local capacity = tonumber(ARGV[1]) " +
            "local refillTokens = tonumber(ARGV[2]) " +
            "local refillPeriod = tonumber(ARGV[3]) * 1000 " +
            "local now = tonumber(ARGV[4]) " +
            "local data = redis.call('HMGET', key, 'tokens', 'lastRefill') " +
            "local tokens = tonumber(data[1]) " +
            "local lastRefill = tonumber(data[2]) " +
            "if tokens == nil then tokens = capacity end " +
            "if lastRefill == nil then lastRefill = now end " +
            "local elapsed = now - lastRefill " +
            "local refills = math.floor(elapsed / refillPeriod) " +
            "if refills > 0 then " +
            "  tokens = math.min(capacity, tokens + refills * refillTokens) " +
            "  lastRefill = lastRefill + refills * refillPeriod " +
            "end " +
            "local allowed = 0 " +
            "if tokens > 0 then " +
            "  tokens = tokens - 1 " +
            "  allowed = 1 " +
            "end " +
            "redis.call('HMSET', key, 'tokens', tokens, 'lastRefill', lastRefill) " +
            "redis.call('EXPIRE', key, refillPeriod * 2 / 1000) " +
            "return allowed"
        );
        script.setResultType(Long.class);
        return script;
    }
}

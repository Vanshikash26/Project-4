package com.hbb.ratelimiter.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Custom annotation to apply rate limiting on any method (typically controller endpoints).
 *
 * Example:
 *   @RateLimit(key = "user-api", capacity = 5, refillTokens = 1, refillPeriodSeconds = 2)
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RateLimit {
    String key();                       // unique key (per API or per user)
    int capacity() default 10;          // max tokens in the bucket
    int refillTokens() default 5;       // tokens added per refill
    int refillPeriodSeconds() default 10; // refill interval
}

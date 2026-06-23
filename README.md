# Distributed Rate Limiter 🚦

A production-grade **Distributed Rate Limiter** built with **Spring Boot** and **Redis**, implementing the **Token Bucket Algorithm**. Designed to handle high-throughput API rate limiting across multiple application nodes using Redis as a centralized state store.

## 🎯 Features

- ✅ **Token Bucket Algorithm** implementation
- ✅ **Distributed** — works across multiple nodes via Redis
- ✅ **Atomic operations** using Redis Lua scripts (race-condition free)
- ✅ **Per-user / Per-API** rate limiting
- ✅ **Configurable** limits via `application.yml`
- ✅ **Custom annotation** `@RateLimit` for easy usage
- ✅ **HTTP 429** response with `Retry-After` header
- ✅ **Spring AOP** based interceptor

## 🏗️ Architecture

```
Client → API Gateway → [RateLimitInterceptor] → Controller
                              ↓
                          Redis (Lua Script)
                              ↓
                      Token Bucket State
```

## 🛠️ Tech Stack

| Layer        | Technology              |
|--------------|-------------------------|
| Language     | Java 17                 |
| Framework    | Spring Boot 3.2         |
| Cache/Store  | Redis 7                 |
| Build        | Maven                   |
| Container    | Docker, Docker Compose  |
| Testing      | JUnit 5, Mockito        |

## 🚀 Getting Started

### Prerequisites
- Java 17+
- Maven 3.8+
- Docker & Docker Compose

### Run with Docker
```bash
docker-compose up -d
mvn spring-boot:run
```

### Test the API
```bash
# Hit the endpoint 10+ times quickly
curl http://localhost:8080/api/v1/test
```

## 📁 Project Structure

```
src/main/java/com/hbb/ratelimiter/
├── RateLimiterApplication.java
├── annotation/
│   └── RateLimit.java
├── aspect/
│   └── RateLimitAspect.java
├── service/
│   └── RateLimiterService.java
├── config/
│   └── RedisConfig.java
└── controller/
    └── DemoController.java
```

## 📖 How It Works

1. Client hits an API endpoint annotated with `@RateLimit`
2. `RateLimitAspect` intercepts the call
3. A **Lua script** runs atomically on Redis to:
   - Refill tokens based on elapsed time
   - Check if a token is available
   - Consume a token if allowed
4. If allowed → request proceeds. If not → returns **HTTP 429**

## 🧪 Future Enhancements

- [ ] Sliding Window Counter algorithm
- [ ] Leaky Bucket algorithm
- [ ] Prometheus metrics + Grafana dashboard
- [ ] Dynamic rate limit configuration via DB
- [ ] Distributed tracing with OpenTelemetry

## 👨‍💻 Author

Built as a backend system-design showcase project.

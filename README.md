# Distributed Rate Limiter 🚦

[![Live Demo](https://img.shields.io/badge/Live%20Demo-Online-brightgreen)](https://project-4-39ji.onrender.com)
[![Java](https://img.shields.io/badge/Java-17-orange)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2-brightgreen)](https://spring.io/projects/spring-boot)
[![Redis](https://img.shields.io/badge/Redis-7-red)](https://redis.io/)

A production-grade **Distributed Rate Limiter** built with **Spring Boot** and **Redis**, implementing the **Token Bucket Algorithm** with atomic Lua scripts for race-condition-free concurrent access.

## 🌐 Live Demo

🔗 **Try it now:** https://project-4-39ji.onrender.com

| Endpoint | Description |
|----------|-------------|
| `/` | Welcome page |
| `/api/v1/health` | Health check |
| `/api/v1/test` | Rate-limited endpoint (**5 req / 10 sec**) |

🔥 Hit `/api/v1/test` 6 times within 10 seconds → 6th request returns **HTTP 429**.

## 🛠️ Tech Stack

- Java 17, Spring Boot 3.2
- Redis 7 (Upstash Cloud)
- Spring AOP, Maven, Docker
- Deployed on Render

## 👨‍💻 Author

**Vanshika** — B.Tech CSE, R.D. Engineering College (AKTU)

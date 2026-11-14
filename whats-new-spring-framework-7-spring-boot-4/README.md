# What's New in Spring Framework 7 and Spring Boot 4

A comprehensive reference covering the major changes in Spring Framework 7 and Spring Boot 4: Jakarta EE 11, JDK 25 alignment, Boot modularisation, native API versioning, Jackson 3, OpenTelemetry consolidation, Hibernate 7, Spring Security 7, and a step-by-step migration guide.

All four modules are consolidated into a single notebook for easy reference.

## Notebook

[whats-new-spring-framework-7-spring-boot-4.ipynb](whats-new-spring-framework-7-spring-boot-4.ipynb)

## Modules

| Module | Topics |
|---|---|
| 01 | Platform Baselines and Core Configuration Changes — Jakarta EE 11, JDK 25, virtual threads, JSpecify null safety, Spring Boot modularisation, configuration property changes |
| 02 | Web Stack Evolution and Standardized Error Handling — native API versioning, RestClient, Problem Details (RFC 9457), Jackson 3, web breaking changes |
| 03 | Observability, Data Layer Updates, and Application Packaging — OpenTelemetry starter, Hibernate 7 / JPA 3.2, Spring Data 2025.1, AOT repositories, native image changes |
| 04 | Security Defaults, Testing Updates, and the Migration Plan — Spring Security 7 DSL, PathPatternRequestMatcher, simplified CSRF, MockitoBean, RestTestClient, migration checklist |

## Assets

The [`assets/`](assets/) directory contains educational diagrams extracted from the course slides:

| File | Content |
|---|---|
| [`01-spring-boot-modularization.png`](assets/01-spring-boot-modularization.png) | Spring Boot 3 monolithic autoconfigure vs. Spring Boot 4 per-technology modules |
| [`02-request-matcher-evolution.png`](assets/02-request-matcher-evolution.png) | PathPatternRequestMatcher replacing AntPathRequestMatcher and MvcRequestMatcher |

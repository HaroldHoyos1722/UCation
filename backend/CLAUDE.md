# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Commands

```bash
# Build
./gradlew build
./gradlew clean build

# Run (starts on http://localhost:8082/rest/v1.0/ucation)
./gradlew bootRun

# Test
./gradlew test

# Run tests for a specific subproject
./gradlew :entrypoints:test
./gradlew :usecases:test
```

On Windows, use `gradlew.bat` instead of `./gradlew`.

## Architecture

This is a **Spring Boot 4.0.3 REST API** for an educational platform, using **Java 21**, built with **Clean/Hexagonal Architecture (Ports & Adapters)**.

### Layer Structure

```
domain/models/          ← Domain layer: entities, port interfaces, enums, exceptions
applications/usecases/  ← Application layer: use case implementations, port interfaces
infrastructure/
  entrypoints/          ← REST controllers, services, Spring configuration (bootJar lives here)
  driven-adapters/
    postgresql-db/      ← JPA entities, Spring Data repositories, DB adapter
    mail-driven/        ← Gmail SMTP email adapter
```

### Gradle Subprojects

| Module | Artifact |
|--------|----------|
| `:models` | Domain models and interfaces |
| `:usecases` | Application/use-case logic |
| `:driven-postgresql` | PostgreSQL adapter |
| `:driven-mail` | Email adapter |
| `:entrypoints` | REST entry point (bootJar) |

### Dependency Flow

Controllers → Services (entrypoints) → Use Case Ports → Use Case Adapters (usecases) → Repository Ports → DB/Mail Adapters

### Dependency Injection

Beans are manually wired in `infrastructure/entrypoints/src/main/java/.../configuration/BeanConfiguration.java`. When adding a new adapter or use case, register it there.

### Main Entry Point

`infrastructure/entrypoints/src/main/java/co/com/polijic/ucation/UcationApplication.java`  
Scans `co.com.polijic.ucation`, enables JPA repositories and transaction management.

## Key Configuration

`infrastructure/entrypoints/src/main/resources/application.yaml`:
- Server port: **8082**, context path: `/rest/v1.0/ucation`
- PostgreSQL: `localhost:5433`, database `ucation`, user `postgres`
- `ddl-auto: none` — schema changes must be applied manually via SQL scripts
- Mail: Gmail SMTP (`smtp.gmail.com:587`, TLS)

## API Documentation

Swagger UI is available at `/swagger-ui.html` when the app is running (SpringDoc OpenAPI is included).

## Domain Concepts

- **Usuario** — base user (can be `Estudiante` or `Mentor`)
- **Dominio** — reference/lookup data (domain values)
- **RecuperarContrasena** — password recovery flow
- Enums live in `domain/models/src/main/java/.../enums/` (e.g., `TipoUsuarioEnum`, `MensajeEnum`)

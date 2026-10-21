# ms-barriodigital-catalog

Microservicio encargado de los tipos de trámite, requisitos y cupos de **BarrioDigital**.

## Responsabilidades

- Crear tipos de trámite.
- Consultar tipos de trámite.
- Modificar tipos de trámite.
- Gestionar requisitos.
- Gestionar cupos.
- Consultar disponibilidad.

## Stack tecnológico

- Java
- Spring Boot
- Spring Web
- Spring Data JPA
- Hibernate
- Maven
- MySQL
- Spring Security
- Eureka Client
- Spring Boot Actuator
- Docker

## Puerto

```text
8082
```

## Base de datos

```text
barriodigital_catalog_db
```

## API

```http
GET   /api/v1/procedure-types
POST  /api/v1/procedure-types
GET   /api/v1/procedure-types/{id}
PUT   /api/v1/procedure-types/{id}
GET   /api/v1/procedure-types/{id}/availability
PATCH /api/v1/procedure-types/{id}/quota
```

## Modelo principal

```text
ProcedureType
├── id
├── name
├── description
├── dailyQuota
├── availableQuota
├── requirements
└── active
```

## Integraciones

Principal consumidor:

```text
ms-barriodigital-requests
```

Flujo:

```text
Requests
   ↓ REST
Catalog
   ↓
MySQL
```

## Seguridad

- JWT.
- Spring Security.
- Amazon Cognito.
- RBAC.

## Observabilidad

- Spring Boot Actuator.
- Health checks.
- Logs.
- Amazon CloudWatch.

## Estructura esperada

```text
src/
├── main/
│   ├── java/
│   │   └── cl/brarriodigital/catalog/
│   │       ├── controller/
│   │       ├── dto/
│   │       │   ├── request/
│   │       │   └── response/
│   │       ├── exception/
│   │       ├── model/
│   │       ├── repository/
│   │       ├── service/
│   │       └── CatalogMsApplication.java
│   └── resources/
│       └── application.properties
└── test/
```

## Alcance de la primera etapa

Este primer incremento establece la estructura por capas. La implementación de
los endpoints y reglas de negocio definidos en el contrato se realizará en
incrementos posteriores. No se incorpora mensajería en esta etapa.

## Contrato

```text
barriodigital-contracts/openapi/catalog.openapi.yaml
```

## Ejecución

```bash
./mvnw spring-boot:run
```

## Docker

```bash
docker build -t barriodigital/catalog:1.0.0 .
```

## Estrategia Git

```text
main
develop
feature/*
fix/*
```

## Estado

🚧 Proyecto en etapa inicial de diseño e implementación.

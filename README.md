# ms-barriodigital-catalog

Microservicio encargado del catálogo de tipos de trámite, requisitos y cupos diarios de **BarrioDigital**.

## Descripción

Centraliza la configuración administrativa de los trámites disponibles en la plataforma.

Su información puede ser consultada por el BFF y por otros microservicios, particularmente Requests.

## Responsabilidades

- Crear tipos de trámite.
- Consultar tipos de trámite.
- Modificar tipos de trámite.
- Activar/desactivar tipos.
- Gestionar requisitos.
- Gestionar cupos diarios.
- Consultar disponibilidad por fecha.
- Mantener configuración del catálogo.

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

## Puerto local

```text
8082
```

## Base de datos

```text
barriodigital_catalog_db
```

Patrón:

```text
Database per Service
```

Ningún otro servicio debe leer directamente esta base.

## API inicial

```http
GET   /api/v1/procedure-types
GET   /api/v1/procedure-types/{procedureTypeId}
POST  /api/v1/procedure-types
PUT   /api/v1/procedure-types/{procedureTypeId}
PATCH /api/v1/procedure-types/{procedureTypeId}/status

GET   /api/v1/procedure-types/{procedureTypeId}/availability
PUT   /api/v1/procedure-types/{procedureTypeId}/quota
```

Ejemplo de tipo de trámite:

```json
{
  "name": "Reparación de luminaria",
  "description": "Solicitud por luminaria pública defectuosa",
  "dailyQuota": 40,
  "active": true
}
```

## Reglas iniciales

- El cupo diario debe ser configurable.
- Los tipos de trámite pueden ser activados o desactivados.
- Requests debe consumir la API de Catalog en lugar de consultar directamente su base de datos.
- La disponibilidad se calcula para una fecha determinada.

## Integraciones

Consumidores principales:

```text
ms-barriodigital-bff
ms-barriodigital-requests
```

Comunicación:

```text
REST / JSON
```

## Seguridad

Roles esperados:

```text
ADMIN
OPERATOR
```

El rol `ADMIN` tendrá permisos de escritura.

Los usuarios autorizados podrán consultar el catálogo según las reglas establecidas.

## Observabilidad

- Spring Boot Actuator.
- Health checks.
- Logs.
- Métricas.
- Amazon CloudWatch.
- Correlation ID.

## Variables de entorno

```env
SERVER_PORT=8082

MYSQL_HOST=
MYSQL_PORT=3306
MYSQL_DATABASE=barriodigital_catalog_db
MYSQL_USER=
MYSQL_PASSWORD=

EUREKA_SERVER_URL=
COGNITO_ISSUER_URI=
```

## Contratos

Repositorio:

```text
barriodigital-contracts
```

Contrato:

```text
OpenAPI
```

## Estructura esperada

```text
src/
├── main/
│   ├── java/
│   │   └── cl/duoc/barriodigital/catalog/
│   │       ├── config/
│   │       ├── controller/
│   │       ├── dto/
│   │       ├── entity/
│   │       ├── exception/
│   │       ├── mapper/
│   │       ├── repository/
│   │       ├── security/
│   │       └── service/
│   └── resources/
│       └── application.yml
└── test/
```

## Ejecución local

```bash
./mvnw spring-boot:run
```

## Docker

```bash
docker build -t barriodigital/catalog:1.0.0 .
```

## CI/CD

Pipeline previsto:

```text
GitHub
↓
GitHub Actions
↓
Tests
↓
SonarQube
↓
Snyk
↓
Docker
↓
Amazon ECR
↓
AWS
```

## Estrategia Git

```text
main
develop
feature/*
fix/*
```

## Estado

🚧 Proyecto en etapa inicial de diseño y construcción.

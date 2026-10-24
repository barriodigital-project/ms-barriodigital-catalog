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
- Lombok
- Spring Boot Actuator
- Docker

## Puerto

```text
8082
```

## Base de datos

```text
catalog_db
```

Al ejecutar desde esta carpeta, Spring carga `DB_PASSWORD` desde el archivo
compartido `../.env` del workspace. Ese archivo local no se versiona.

## API

```http
GET   /api/v1/procedure-types
POST  /api/v1/procedure-types
GET   /api/v1/procedure-types/{id}
PUT   /api/v1/procedure-types/{id}
GET   /api/v1/procedure-types/{id}/availability
PATCH /api/v1/procedure-types/{id}/quota
DELETE /api/v1/procedure-types/{id}
```

Eliminar un tipo de trámite responde HTTP 204.

Al crear un tipo, `availableQuota` parte igual a `dailyQuota`. Un PUT reinicia
el cupo disponible al cupo diario enviado. La disponibilidad es verdadera solo
si el tipo está activo y conserva cupos. El PATCH rechaza cupos mayores al
cupo diario con HTTP 409.

```bash
curl http://localhost:8082/api/v1/procedure-types
```

```json
[
  {
    "id": "6dcc94c3-d355-4ff4-9cb2-52e15952ffdc",
    "name": "Licencia de conducir",
    "description": "Renovación de licencia",
    "dailyQuota": 12,
    "availableQuota": 8,
    "requirements": ["Cédula vigente"],
    "active": true
  }
]
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

El BFF/gateway autentica al usuario y valida permisos. Este servicio no valida
tokens y debe aceptar solo tráfico interno; no exponer directamente el puerto
`8082`.

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

Se implementan los endpoints definidos en OpenAPI, el modelo JPA, persistencia,
validaciones y manejo de errores. Este servicio no usa mensajería.

## Configuración local

El servicio escucha en `localhost:8082` y usa MySQL en `localhost:3306` por
defecto. Se pueden sobrescribir `DB_HOST`, `DB_PORT`, `DB_NAME`, `DB_USERNAME`
y `DB_PASSWORD`. `JPA_DDL_AUTO` controla el DDL de Hibernate (por defecto
`update`; usar migraciones controladas en producción). Actuator expone
`health`, `info` y `metrics` bajo `/actuator`.

## Service Discovery

El servicio se registra en Eureka. Por defecto usa
`http://localhost:8761/eureka/`; se puede cambiar mediante `EUREKA_SERVER_URL`.

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

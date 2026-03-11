# Gestion Profesion

API REST construida con Spring Boot para gestionar `personas` y `profesiones`. El proyecto ya incluye persistencia con JPA, documentación OpenAPI/Swagger, manejo global de errores y autenticación básica con JWT.

La intención actual del repositorio es académica: mostrar una estructura típica de backend Java con capas `controller`, `service`, `repository`, DTOs, mappers y seguridad stateless.

## Estado actual

- CRUD de `profesion` disponible y protegido con JWT.
- CRUD parcial de `persona`: crear, actualizar, consultar y listar; el endpoint de eliminación existe pero su lógica todavía no está implementada.
- `GET /api/persona/public` es público.
- Autenticación por `POST /auth/login` con credenciales hardcodeadas.
- Swagger UI y OpenAPI disponibles al ejecutar la aplicación.
- Configuración actual orientada a MySQL local.

## Stack

- Java 17
- Spring Boot 3.5.11
- Spring Web
- Spring Data JPA
- Spring Validation
- Spring Security
- JWT (`jjwt`)
- MySQL
- Springdoc OpenAPI / Swagger UI
- Lombok

## Estructura de documentación

- [Documentación técnica](docs/README.md)
- [PRDs](prds/README.md)

## Estructura del proyecto

```text
src/main/java/com/s1/gestion_profesion
├── auth          -> login y emisión de token JWT
├── config        -> seguridad, JWT y OpenAPI
├── controller    -> endpoints REST
├── dto           -> contratos de entrada y salida
├── exception     -> manejo global de errores
├── mapper        -> transformación entidad <-> DTO
├── model         -> entidades JPA
├── repository    -> acceso a datos
└── service       -> lógica de negocio
```

## Requisitos previos

- Java 17
- MySQL disponible localmente
- Maven Wrapper incluido en el repo

## Configuración local

La aplicación espera una base de datos MySQL llamada `gestion_profesion` y, debido a que `spring.jpa.hibernate.ddl-auto=none`, las tablas deben existir antes de levantar el servicio.

Configuración actual en `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/gestion_profesion
spring.datasource.username=root
spring.datasource.password=123
spring.jpa.hibernate.ddl-auto=none
spring.jpa.show-sql=true
```

## Ejecución

```bash
./mvnw spring-boot:run
```

Si el arranque es exitoso, por defecto la API queda disponible en:

- `http://localhost:8080`
- Swagger UI: `http://localhost:8080/swagger-ui/index.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

## Autenticación

El login actual usa credenciales fijas:

```json
{
  "username": "admin",
  "password": "1234"
}
```

Request:

```http
POST /auth/login
Content-Type: application/json
```

Response:

```json
{
  "token": "jwt..."
}
```

Usa el token en los endpoints protegidos:

```http
Authorization: Bearer <token>
```

## Endpoints principales

| Método | Ruta | Acceso | Estado actual |
| --- | --- | --- | --- |
| POST | `/auth/login` | Público | Genera JWT |
| GET | `/api/persona/public` | Público | Lista personas |
| POST | `/api/persona` | Protegido | Crea persona |
| PUT | `/api/persona/{id}` | Protegido | Actualiza persona |
| GET | `/api/persona/edad?edad=26` | Protegido | Filtra personas por edad |
| GET | `/api/persona/{id}` | Protegido | Consulta persona por id |
| DELETE | `/api/persona/{id}` | Protegido | Endpoint expuesto, lógica pendiente |
| GET | `/api/profesion` | Protegido | Lista profesiones |
| POST | `/api/profesion` | Protegido | Crea profesión |
| PUT | `/api/profesion/{id}` | Protegido | Actualiza profesión |
| GET | `/api/profesion/{id}` | Protegido | Consulta profesión por id |
| DELETE | `/api/profesion/{id}` | Protegido | Elimina profesión |

## Limitaciones conocidas

- El secreto JWT y las credenciales de acceso están en código fuente.
- No hay usuarios, roles ni integración con una tabla de autenticación.
- No existen scripts SQL ni migraciones versionadas dentro del repositorio.
- Parte de los `not found` termina en `500` porque algunos servicios lanzan `RuntimeException` en lugar de `EntityNotFoundException`.
- Las validaciones de `ProfesionRequestDTO` no se ejecutan hoy en controller porque falta `@Valid`.
- El CORS está limitado a `http://127.0.0.1:5500` y `http://localhost:5500`.

## Documentación adicional

- [Arquitectura](docs/arquitectura.md)
- [API](docs/api.md)
- [Modelo de datos](docs/modelo-datos.md)
- [Estado actual](docs/estado-actual.md)
- [PRD del MVP actual](prds/prd-mvp-api-gestion-profesion.md)
- [PRD de siguiente iteración](prds/prd-siguiente-iteracion.md)

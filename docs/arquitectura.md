# Arquitectura

## Resumen

El proyecto sigue una arquitectura por capas típica de Spring Boot:

1. `controller`: expone endpoints HTTP.
2. `service`: contiene la lógica de negocio.
3. `repository`: interactúa con la base de datos mediante Spring Data JPA.
4. `model`: entidades JPA.
5. `dto` y `mapper`: separan el modelo persistente del contrato HTTP.
6. `config`: seguridad JWT, filtro de autenticación y OpenAPI.
7. `exception`: centraliza el manejo de errores.

## Flujo principal de una petición

1. El request entra por un `controller`.
2. Si la ruta es protegida, `JwtFilter` revisa el header `Authorization`.
3. El controller recibe DTOs de entrada y delega al `service`.
4. El service consulta o persiste usando `repository`.
5. El resultado se transforma a DTO de salida por medio de los `mapper`.
6. Si ocurre una excepción conocida, `GlobalExceptionHandler` construye la respuesta de error.

## Paquetes relevantes

### `auth`

- `AuthController`: expone `POST /auth/login`.
- `LoginRequest`: contrato de entrada para autenticación.
- `LoginResponse`: record disponible, aunque el controller hoy responde con `Map<String, String>`.

### `config`

- `SecurityConfig`: define el `SecurityFilterChain`.
- `JwtFilter`: valida el token JWT en cada petición protegida.
- `JwtService`: genera y valida tokens firmados con HS256.
- `OpenAPIConfig`: personaliza título, versión y descripción de Swagger.

### `controller`

- `PersonaController`: endpoints de persona.
- `ProfesionController`: endpoints de profesión.

### `service`

- `PersonaServiceImpl`: lógica de personas.
- `ProfesionServiceImpl`: lógica de profesiones.

### `repository`

- `PersonaRepository`: CRUD JPA y búsqueda por nombre / edad.
- `ProfesionRepository`: CRUD JPA y utilidades por nombre.

### `exception`

- `GlobalExceptionHandler`: centraliza respuestas 400, 404 y 500.
- `BusinessRuleException`: representa errores de negocio.
- `ErrorResponse`: payload estándar de error.

## Seguridad

### Autenticación actual

- El login se realiza contra credenciales fijas: `admin / 1234`.
- Si son válidas, se genera un JWT de 30 minutos.
- No existe persistencia de usuarios ni manejo de roles.

### Endpoints públicos

- `POST /auth/login`
- `OPTIONS /**`
- `GET /api/persona/public`

### Endpoints protegidos

Todo lo demás requiere token JWT.

### CORS

Actualmente solo se permiten estos orígenes:

- `http://127.0.0.1:5500`
- `http://localhost:5500`

## Persistencia

- La base de datos configurada es MySQL.
- `spring.jpa.hibernate.ddl-auto=none` indica que Hibernate no crea ni actualiza tablas.
- No se encontraron scripts SQL ni migraciones versionadas en el repositorio.

## Decisiones visibles en el código

- Se usan `record` para DTOs de request y response.
- Los mappers son manuales, sin MapStruct.
- El modelo `Persona` referencia a `Profesion` con `@ManyToOne(fetch = LAZY)`.
- Swagger se apoya en `springdoc-openapi-starter-webmvc-ui`.

## Riesgos técnicos actuales

- Clave JWT hardcodeada en código fuente.
- Credenciales de acceso hardcodeadas.
- Respuestas de error inconsistentes para algunos casos `not found`.
- Endpoint de eliminación de persona sin implementación.
- Validación de profesión declarada en DTO, pero no activada en controller.

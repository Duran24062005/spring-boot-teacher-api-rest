# API

## Base URL

```text
http://localhost:8080
```

## Autenticación

### `POST /auth/login`

Autentica al usuario y retorna un JWT.

Request:

```json
{
  "username": "admin",
  "password": "1234"
}
```

Response exitosa:

```json
{
  "token": "jwt..."
}
```

Error por credenciales inválidas:

```json
{
  "timestamp": "2026-03-11T10:00:00",
  "status": 400,
  "message": "Credenciales inválidas",
  "errorCode": "BUSINESS_RULE_VIOLATION"
}
```

## Endpoints de persona

| Método | Ruta | Auth | Descripción |
| --- | --- | --- | --- |
| GET | `/api/persona/public` | No | Lista todas las personas |
| POST | `/api/persona` | Sí | Crea una persona |
| PUT | `/api/persona/{id}` | Sí | Actualiza una persona |
| GET | `/api/persona/edad?edad=26` | Sí | Lista personas con edad mayor al valor enviado |
| GET | `/api/persona/{id}` | Sí | Consulta una persona por id |
| DELETE | `/api/persona/{id}` | Sí | Endpoint publicado, pero la lógica no está implementada |

### Request de creación / actualización

```json
{
  "documento": "1007999211",
  "nombre": "David",
  "apellido": "Martinez",
  "edad": 27,
  "salario": 3500000,
  "profesionId": 1
}
```

### Response

```json
{
  "id": 1,
  "documento": "1007999211",
  "nombre": "David",
  "apellido": "Martinez",
  "edad": 27,
  "salario": 3500000,
  "profesion": {
    "id": 1,
    "nombre": "Sistemas",
    "descripcion": "mundo tech"
  }
}
```

## Endpoints de profesión

| Método | Ruta | Auth | Descripción |
| --- | --- | --- | --- |
| GET | `/api/profesion` | Sí | Lista profesiones |
| POST | `/api/profesion` | Sí | Crea una profesión |
| PUT | `/api/profesion/{id}` | Sí | Actualiza una profesión |
| GET | `/api/profesion/{id}` | Sí | Consulta una profesión por id |
| DELETE | `/api/profesion/{id}` | Sí | Elimina una profesión |

### Request de creación / actualización

```json
{
  "nombre": "Sistemas",
  "descripcion": "mundo tech"
}
```

### Response

```json
{
  "id": 1,
  "nombre": "Sistemas",
  "descripcion": "mundo tech"
}
```

## Validaciones actuales

### Persona

Las validaciones activas están en `POST /api/persona` y `PUT /api/persona/{id}`:

- `apellido`: mínimo 2 y máximo 25 caracteres.
- `edad`: obligatoria y positiva.

Observaciones:

- `documento`, `nombre`, `salario` y `profesionId` no tienen validaciones declarativas en el DTO.
- Si `profesionId` no existe al crear, hoy el servicio lanza `RuntimeException`, por lo que el resultado real puede ser `500`.

### Profesión

`ProfesionRequestDTO` sí tiene restricciones (`@NotBlank`, `@Size`), pero en el controller faltan `@Valid` en `POST` y `PUT`. En consecuencia, esas validaciones no se aplican hoy automáticamente.

## Errores

### Respuesta estándar

Para varios errores se usa esta estructura:

```json
{
  "timestamp": "2026-03-11T10:00:00",
  "status": 404,
  "message": "mensaje",
  "errorCode": "codigo"
}
```

### Casos manejados por `GlobalExceptionHandler`

- `MethodArgumentNotValidException` -> `400`
- `BusinessRuleException` -> `400`
- `EntityNotFoundException` -> `404`
- `NoHandlerFoundException` -> `404`
- `HttpMessageNotReadableException` -> `400`
- `Exception` genérica -> `500`

### Inconsistencias actuales

- Algunos `not found` devuelven `404`.
- Otros devuelven `500`, porque el servicio usa `RuntimeException` y cae en el manejador genérico.

## Swagger

Con la aplicación levantada:

- Swagger UI: `http://localhost:8080/swagger-ui/index.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

# Modelo de datos

## Entidades

El dominio actual gira alrededor de dos entidades: `Profesion` y `Persona`.

## `Profesion`

Tabla: `profesion`

| Campo | Tipo Java | Restricción |
| --- | --- | --- |
| `id` | `Long` | PK, autoincremental |
| `nombre` | `String` | `nullable = false` |
| `descripcion` | `String` | `nullable = false` |

## `Persona`

Tabla: `persona`

| Campo | Tipo Java | Restricción |
| --- | --- | --- |
| `id` | `Long` | PK, autoincremental |
| `documento` | `String` | `nullable = false` |
| `nombre` | `String` | `nullable = false` |
| `apellido` | `String` | `nullable = false` |
| `edad` | `Integer` | `nullable = false` |
| `salario` | `BigDecimal` | `nullable = false` |
| `profesion` | `Profesion` | FK obligatoria hacia `profesion_id` |

## Relación

- Una `Persona` pertenece a una sola `Profesion`.
- Una `Profesion` puede estar asociada a muchas `Persona`.

Representación:

```text
Profesion 1 ---- N Persona
```

## Consideraciones de persistencia

- `Persona.profesion` está configurada con `fetch = FetchType.LAZY`.
- La columna FK esperada en MySQL es `profesion_id`.
- Como `ddl-auto=none`, la estructura debe existir previamente en la base de datos.

## Consultas derivadas disponibles

### `PersonaRepository`

- `findByNombreIgnoreCase(String nombre)`
- `findByEdadGreaterThan(Integer edad)`

### `ProfesionRepository`

- `findByNombreIgnoreCase(String nombre)`
- `existsByNombre(String nombre)`
- `countByNombre(String nombre)`

## DTOs expuestos por la API

### Entrada

- `ProfesionRequestDTO`
- `PersonaRequestDTO`

### Salida

- `ProfesionResponseDTO`
- `PersonaResponseDTO`

`PersonaResponseDTO` ya incluye el objeto `ProfesionResponseDTO` embebido, por lo que la API responde con información desnormalizada y útil para frontend.

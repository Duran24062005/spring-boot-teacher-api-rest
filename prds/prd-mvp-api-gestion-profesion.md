# PRD MVP: API de Gestión de Profesión y Persona

## Resumen

Construir una API REST educativa para gestionar profesiones y personas asociadas, con autenticación básica mediante JWT y documentación Swagger.

## Problema

Se necesita un backend simple para demostrar:

- modelado relacional básico,
- CRUD sobre dos entidades relacionadas,
- separación por capas,
- autenticación stateless,
- documentación de endpoints.

## Objetivo del producto

Permitir que un consumidor HTTP autenticado administre profesiones y personas, dejando una operación pública de consulta para fines de demostración.

## Usuarios objetivo

- Estudiantes que están aprendiendo Spring Boot.
- Frontend local que consume la API.
- Docentes o revisores que validan conceptos de API REST.

## Alcance del MVP actual

### Incluido

- Login con JWT.
- CRUD de profesiones.
- Alta, edición, consulta y listado de personas.
- Filtro de personas mayores a una edad.
- Endpoint público para listar personas.
- Swagger/OpenAPI.
- Manejo global de excepciones.

### Parcial

- Eliminación de persona: el endpoint existe, la lógica no.

### No incluido

- Registro de usuarios.
- Roles y permisos.
- Refresh token.
- Migraciones de base de datos.
- Auditoría.
- Paginación y filtros avanzados.
- Suite de pruebas completa.

## Requisitos funcionales

| ID | Requisito | Estado |
| --- | --- | --- |
| RF-01 | El sistema debe autenticar por `username` y `password` | Implementado |
| RF-02 | El sistema debe emitir un JWT al autenticarse correctamente | Implementado |
| RF-03 | El sistema debe proteger los endpoints privados con JWT | Implementado |
| RF-04 | El sistema debe permitir crear, editar, consultar, listar y eliminar profesiones | Implementado |
| RF-05 | El sistema debe permitir crear, editar, consultar y listar personas | Implementado |
| RF-06 | El sistema debe permitir consultar personas mayores a una edad | Implementado |
| RF-07 | El sistema debe exponer un listado público de personas | Implementado |
| RF-08 | El sistema debe permitir eliminar personas | Parcial |
| RF-09 | El sistema debe documentar la API con Swagger | Implementado |

## Requisitos no funcionales

| ID | Requisito | Estado |
| --- | --- | --- |
| RNF-01 | La API debe usar Java 17 y Spring Boot | Implementado |
| RNF-02 | La API debe usar persistencia relacional con JPA | Implementado |
| RNF-03 | La API debe devolver respuestas JSON | Implementado |
| RNF-04 | La API debe manejar errores comunes de forma centralizada | Implementado con inconsistencias |
| RNF-05 | La API debe ser utilizable desde frontend local | Implementado para `localhost:5500` |
| RNF-06 | La configuración sensible debe estar externalizada | No implementado |

## Métricas de éxito del MVP

- Se puede obtener un token válido mediante login.
- Con el token, se pueden operar profesiones.
- Con el token, se pueden operar personas excepto eliminación.
- Sin token, el usuario solo puede usar login y el listado público de personas.
- Swagger describe y permite probar los endpoints disponibles.

## Riesgos y restricciones actuales

- El acceso depende de credenciales fijas en código fuente.
- El secreto JWT está hardcodeado.
- La base de datos debe existir previamente.
- Algunos errores funcionales terminan como `500`.

## Criterio de cierre de esta fase

El MVP se considera documentado y funcional para fines académicos cuando:

- la aplicación levanta con una base de datos válida,
- el flujo login -> token -> consumo de endpoints protegidos funciona,
- y la documentación refleja correctamente los límites actuales del sistema.

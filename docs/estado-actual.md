# Estado actual

## Funcionalidad implementada

### Seguridad

- Login con JWT.
- Filtro de autenticación para endpoints protegidos.
- Política stateless con Spring Security.

### Profesiones

- Crear profesión.
- Actualizar profesión.
- Listar profesiones.
- Buscar profesión por id.
- Eliminar profesión.

### Personas

- Crear persona.
- Actualizar persona.
- Listar personas.
- Consultar persona por id.
- Filtrar personas por edad.
- Exponer listado público sin token.

### Documentación y soporte

- Swagger / OpenAPI.
- DTOs para requests y responses.
- Manejo global de excepciones.

## Brechas visibles en la implementación

- `DELETE /api/persona/{id}` no elimina nada todavía.
- `LoginResponse` existe pero no se usa en `AuthController`.
- Parte de la capa service lanza `RuntimeException`, lo que degrada la semántica HTTP esperada.
- No hay pruebas funcionales ni unitarias más allá del `contextLoads`.
- No hay migraciones de base de datos ni scripts DDL en el repositorio.

## Comportamientos que conviene considerar al integrar

- El frontend debe autenticarse primero para casi todas las operaciones.
- Solo `GET /api/persona/public` es público dentro del dominio funcional.
- El backend asume que la base de datos y sus tablas ya fueron creadas manualmente.
- El CORS actual está restringido a dos orígenes locales.

## Nivel de madurez

Estado: MVP técnico / académico.

Interpretación práctica:

- Sirve para demostrar la estructura de una API REST con seguridad JWT.
- Todavía no está endurecida para un despliegue productivo.

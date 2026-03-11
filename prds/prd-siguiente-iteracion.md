# PRD Siguiente Iteración: Endurecimiento y cierre funcional

## Objetivo

Cerrar las brechas visibles del MVP actual para pasar de una API académica a una base más sólida para integración real.

## Problemas a resolver

- Existe un endpoint de eliminación de persona sin implementación.
- La semántica de errores no es consistente.
- Las credenciales y el secreto JWT están expuestos en código.
- No hay esquema versionado de base de datos.
- La validación de profesiones no se ejecuta en controller.
- La cobertura de pruebas es prácticamente nula.

## Alcance propuesto

### Prioridad alta

| ID | Requisito | Criterio de aceptación |
| --- | --- | --- |
| RF-10 | Implementar eliminación real de persona | `DELETE /api/persona/{id}` elimina el registro y responde `204`; si no existe, responde `404` |
| RF-11 | Unificar manejo de `not found` | Búsquedas inexistentes responden consistentemente `404` |
| RF-12 | Activar validación en profesión | `POST` y `PUT` de profesión rechazan payloads inválidos con `400` |
| RF-13 | Externalizar secretos y credenciales | JWT secret y credenciales ya no viven en el código fuente |

### Prioridad media

| ID | Requisito | Criterio de aceptación |
| --- | --- | --- |
| RF-14 | Incorporar migraciones de base de datos | El proyecto puede inicializar o versionar esquema con Flyway o Liquibase |
| RF-15 | Reemplazar login hardcodeado por usuarios persistidos | El login consulta usuarios desde base de datos o proveedor equivalente |
| RF-16 | Incorporar pruebas de controller y service | Existen pruebas automatizadas sobre rutas y reglas principales |

### Prioridad baja

| ID | Requisito | Criterio de aceptación |
| --- | --- | --- |
| RF-17 | Agregar paginación y filtros por nombre | Listados aceptan parámetros adicionales de consulta |
| RF-18 | Definir roles básicos | Existe separación mínima entre lectura y escritura |

## Requisitos no funcionales

| ID | Requisito | Criterio de aceptación |
| --- | --- | --- |
| RNF-07 | La configuración debe ser portable entre entornos | Variables sensibles se cargan por `application-*.properties` o variables de entorno |
| RNF-08 | Los errores deben ser trazables y coherentes | El contrato de error es uniforme para 400, 404 y 500 |
| RNF-09 | La API debe seguir siendo documentable en Swagger | La documentación se mantiene alineada con los cambios |

## Dependencias

- Decisión sobre estrategia de usuarios y roles.
- Decisión sobre herramienta de migraciones.
- Disponibilidad de entorno MySQL para pruebas.

## Fuera de alcance de esta iteración

- Multi-tenant.
- OAuth2 / integración con proveedores externos.
- Panel administrativo.
- Observabilidad avanzada.

## Resultado esperado

Al finalizar esta iteración, la API debe:

- comportarse de forma consistente ante errores,
- tener el CRUD realmente cerrado para `persona`,
- reducir riesgos de seguridad básicos,
- y ser más simple de ejecutar desde cero en otro entorno.

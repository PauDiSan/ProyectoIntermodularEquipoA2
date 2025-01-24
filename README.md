# ProyectoIntermodularEquipoA2

http://localhost:8080/swagger-ui/index.html

# Documentación de la API - Equipo A2

## Realizado por el Equipo 2
* Raúl Casas Gómez
* Francisco Sitjar de Cos-Estrada
* Paula Díaz Santos
* Raúl Buenaga

## Tabla de contenidos

1. [Introducción](#introducción)
2. [Configuración](#configuración)
3. [Estructura de ProjectStore](#estructura-de-projectstore)
4. [Modelos (Entities)](#modelos-entities)
5. [Repositorios (Repositories)](#repositorios-repositories)
6. [Controladores (EndPoints)](#controladores-endpoints)
7. [Servicios (Services)](#servicios)
8. [Conclusión](#conclusión)

## Introducción

Esta API esta basada en la Base De Datos de Proyectos, en la cual está creada para el guardado y el procesado de los
proyectos realizados por el alumnado del instituto IES MIGUEL HERRERO, para su consulta y su gestión.

## Configuración

Esta sección describe los ajustes necesarios en el archivo de configuración de la ProjectStore para que funcione correctamente. Los parámetros están definidos en el archivo `application.properties`.

### Propiedades principales

A continuación, se muestran las propiedades utilizadas en la API ProjectStore:

```properties
# Nombre de la aplicación
spring.application.name=proyectosapi

# Configuración de la base de datos
spring.datasource.url=jdbc:mysql://localhost:3307/proyectos
spring.datasource.username=root
spring.datasource.password=
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# Puerto en el que se ejecutará la aplicación
server.port=4000

# Prefijo para los endpoints (URL base)
server.servlet.context-path=/

# Configuración de OpenAPI y Swagger
# Habilita la generación de documentación de la API
springdoc.api-docs.enabled=true 
# Personaliza la URL de Swagger UI        
springdoc.swagger-ui.path=/swagger-ui.html 

# Configuración de archivos subidos
# Habilita la subida de archivos
spring.servlet.multipart.enabled=true 
# Tamaño máximo permitido por archivo         
spring.servlet.multipart.max-file-size=50GB    
# Tamaño máximo permitido por solicitud
spring.servlet.multipart.max-request-size=50GB 
```
## Estructura de la API

Este proyecto sigue una estructura organizada en paquetes que representan las capas principales de la arquitectura.

### Estructura de Carpetas

```plaintext
src/main/java
└── com.example.gestorapi
    ├── controller      # Controladores (Contiene los controladores que definen los endpoints de la API)
    ├── model           # Clases de modelo y enums (Incluye las entidades que representan tablas de la base de datos)
    ├── repository      # Repositorios (Define las interfaces para interactuar con la base de datos mediante JPA)
    ├── security        # Clases relacionadas con la seguridad de la API (Solo está en las ramas que lo implementen)
        └── DTO         # Guarda clases DTO (Solo está en las ramas que lo implementen)
    └── service         # Servicios (subida de ficheros)
        ├── files       # Clase para subir archivos
        └── impl        # Las clases impl de los servicios
```
```
```

## Modelos (Entities)

Los modelos en esta API de ProjectStore representan las entidades principales del sistema y su mapeo a la base de datos. Cada clase dentro del paquete `model` utiliza anotaciones de JPA (Java Persistence API) para definir su estructura, relaciones y comportamiento en la base de datos.

### Principales entidades del proyecto

1. **Actividades**: Representa una actividad registrada en el sistema.
2. **Contratos**: Guarda la actividad, tranposrte e información basica del contrato.
3. **Cursos**: Representa los cursos de los que se disponen en el instituto.
4. **Emp_transporte**: Representa las empresas transporte que se han contratado para realizar alguna actividad.
5. **Fotos**: Guarda la informacion necesaria para acceder a las fotos y a que actividad pertenecen.
6. **Grupos**: Representa el grupo, guardando el curso al que pertenecen, su tutor y si aun existe.
7. **Profesores**: Representa un profesor y toda su informacion relevante como email, nombre y apellidos, su contraseña (encriptada), etc...
8. **Users**: (Tabla creada e implementada para aumentar la seguridad de la API, solo la utilizan las ramas relacionadas con la seguridad) Guarda la informacion para saber quien esta accediendo a la API.

Además, se utilizan los **Enums** para valores constantes como etapa, rol, estado,...

## Repositorios (Repositories)

Los repositorios en esta API se encargan de almacenar consultas para poder acceder a los datos de la BBDD.

### Principales Repositorios

| Repositorio               | Entidad asociada | Descripción                                              |
|---------------------------|------------------|----------------------------------------------------------|
| `ActividadRepository`     | `Actividad`      | Maneja las consultas relacionadas con alumnos. |
| `ContratoRepository`      | `Ciclo`          | Proporciona acceso a los ciclos formativos.              |
| `CursoRepository`         | `Curso`          | Gestiona los datos de los proyectos.                     |
| `DepartamentoRepository`  | `Departamento`   | Acceso y manipulación de datos relacionados con los profesores. |
| `EmpTransporteRepository` | `EmpTransporte`  | Relación entre alumnos y proyectos.                      |
| `FotoRepository`          | `Fotos`          | Relación entre profesores y proyectos evaluados.         |
| `ProfesorRepository`      | `Profesor`       | Relación entre profesores y proyectos evaluados.         |



---

### Ejemplo de Repositorio: `ProfesorRepository`

El repositorio de JPA `ProfesorRepository` permite tener consultas prediseñadas para casos concretos:
- `Profesor findByCorreoAndPassword(String correo, String password)`: Método de iniciar sesión.
- `Profesor findByCorreo(String correo)`: Método para encontrar el profesor por correo.

## Service
Los services en esta API representan la capa de acceso a datos

 Service                   | Entidad asociada | Descripción                                              |
|---------------------------|------------------|----------------------------------------------------------|
| `ActividadService`        | `Actividad`      | Maneja las consultas relacionadas con alumnos. |
| `ContratoService`      | `Ciclo`          | Proporciona acceso a los ciclos formativos.              |
| `CursoService`         | `Curso`          | Gestiona los datos de los proyectos.                     |
| `DepartamentoService`  | `Departamento`   | Acceso y manipulación de datos relacionados con los profesores. |
| `EmpTransporteService` | `EmpTransporte`  | Relación entre alumnos y proyectos.                      |
| `FotoService`          | `Fotos`          | Relación entre profesores y proyectos evaluados.         |
| `ProfesorService`      | `Profesor`       | Relación entre profesores y proyectos evaluados.         |


# Controladores (EndPoints)

En esta sección se detallan los controladores de la API, los cuales son responsables de manejar las solicitudes HTTP entrantes y devolver las respuestas adecuadas. Cada controlador está vinculado a una ruta específica y proporciona diferentes operaciones sobre los recursos de la aplicación.

A continuación, se describe el controlador `AlumnoController`, presentando un resumen  de todos los endpoints disponibles en este controlador.



# Endpoints de la API - ActividadController

| **Método HTTP** | **Endpoint**                            | **Descripción**                                              | **Respuesta Exitosa**                                             | **Código de Estado**  | **Parámetros**                                                    |
|-----------------|-----------------------------------------|--------------------------------------------------------------|------------------------------------------------------------------|-----------------------|-------------------------------------------------------------------|
| **GET**         | /acex/actividades                       | Obtiene todas las actividades.                               | Lista de actividades                                              | `200 OK`              | Ninguno                                                           |
| **GET**         | /acex/actividades/{id}                  | Obtiene una actividad específica por ID.                      | Actividad específica                                              | `200 OK`              | `id` (int) - ID de la actividad                                  |
| **GET**         | /acex/actividades/excel                 | Descarga un archivo Excel con la información de una actividad. | Archivo Excel con la información de la actividad                  | `200 OK`              | `actividad` (int) - ID de la actividad para generar el Excel      |
| **POST**        | /acex/actividades                       | Crea una nueva actividad.                                     | Actividad recién creada                                           | `201 Created`         | `Actividad` (JSON) - Datos de la actividad                        |
| **PUT**         | /acex/actividades/{id}                  | Actualiza una actividad existente por ID.                     | Actividad actualizada                                             | `200 OK`              | `id` (int) - ID de la actividad, `Actividad` (JSON) - Datos a actualizar |
| **DELETE**      | /acex/actividades/{id}                  | Elimina una actividad específica por ID.                      | Mensaje de confirmación de eliminación                             | `200 OK`              | `id` (int) - ID de la actividad                                  |
| **PUT**         | /acex/actividades/{id}/folleto          | Subir un folleto (archivo PDF) a la actividad por ID.         | Mensaje de éxito al subir el folleto                               | `200 OK`              | `id` (int) - ID de la actividad, `fichero` (MultipartFile) - Archivo PDF |
| **GET**         | /acex/actividades/{id}/folleto          | Descargar el folleto asociado a una actividad por ID.         | Archivo PDF del folleto                                           | `200 OK`              | `id` (int) - ID de la actividad                                  |

# Endpoints de la API - ContratoController

| **Método HTTP** | **Endpoint**                            | **Descripción**                                              | **Respuesta Exitosa**                                             | **Código de Estado**  | **Parámetros**                                                    |
|-----------------|-----------------------------------------|--------------------------------------------------------------|------------------------------------------------------------------|-----------------------|-------------------------------------------------------------------|
| **GET**         | /acex/contratos                         | Obtiene todos los contratos.                                 | Lista de contratos                                               | `200 OK`              | Ninguno                                                           |
| **GET**         | /acex/contratos/{id}                    | Obtiene un contrato específico por ID.                        | Contrato específico                                               | `200 OK`              | `id` (int) - ID del contrato                                      |
| **POST**        | /acex/contratos                         | Crea un nuevo contrato.                                       | Contrato recién creado                                            | `201 Created`         | `Contrato` (JSON) - Datos del contrato                             |
| **PUT**         | /acex/contratos/{id}                    | Actualiza un contrato existente por ID.                       | Contrato actualizado                                              | `200 OK`              | `id` (int) - ID del contrato, `Contrato` (JSON) - Datos a actualizar |
| **DELETE**      | /acex/contratos/{id}                    | Elimina un contrato específico por ID.                        | Mensaje de confirmación de eliminación                             | `200 OK`              | `id` (int) - ID del contrato                                      |
| **PUT**         | /acex/contratos/{idActividad}/fichero    | Subir un archivo PDF (presupuesto o factura) asociado a un contrato. | Mensaje de éxito al subir el archivo                              | `200 OK`              | `idActividad` (int) - ID de la actividad, `fichero` (MultipartFile) - Archivo PDF, `esPresupuesto` (Boolean) - Indica si es presupuesto o factura |
| **GET**         | /acex/contratos/{idActividad}/fichero    | Descargar un archivo (presupuesto o factura) de un contrato.  | Archivo PDF del contrato                                          | `200 OK`              | `idActividad` (int) - ID de la actividad, `id` (int) - ID del contrato, `esPresupuesto` (Boolean) - Indica si es presupuesto o factura |

# Endpoints de la API - CursoController

| **Método HTTP** | **Endpoint**                            | **Descripción**                                              | **Respuesta Exitosa**                                             | **Código de Estado**  | **Parámetros**                                                    |
|-----------------|-----------------------------------------|--------------------------------------------------------------|------------------------------------------------------------------|-----------------------|-------------------------------------------------------------------|
| **GET**         | /acex/cursos                            | Obtiene todos los cursos.                                    | Lista de cursos                                                  | `200 OK`              | Ninguno                                                           |
| **GET**         | /acex/cursos/{id}                       | Obtiene un curso específico por ID.                           | Curso específico                                                 | `200 OK`              | `id` (int) - ID del curso                                         |
| **POST**        | /acex/cursos                            | Crea un nuevo curso.                                         | Curso recién creado                                              | `201 Created`         | `Curso` (JSON) - Datos del curso                                  |
| **PUT**         | /acex/cursos/{id}                       | Actualiza un curso existente por ID.                          | Curso actualizado                                                | `200 OK`              | `id` (int) - ID del curso, `Curso` (JSON) - Datos a actualizar    |
| **DELETE**      | /acex/cursos/{id}                       | Elimina un curso específico por ID.                           | Mensaje de confirmación de eliminación                            | `200 OK`              | `id` (int) - ID del curso                                         |

# Endpoints de la API - DepartamentoController

| **Método HTTP** | **Endpoint**                            | **Descripción**                                              | **Respuesta Exitosa**                                             | **Código de Estado**  | **Parámetros**                                                    |
|-----------------|-----------------------------------------|--------------------------------------------------------------|------------------------------------------------------------------|-----------------------|-------------------------------------------------------------------|
| **GET**         | /acex/departamentos                     | Obtiene todos los departamentos.                              | Lista de departamentos                                           | `200 OK`              | Ninguno                                                           |
| **GET**         | /acex/departamentos/{id}                | Obtiene un departamento específico por ID.                    | Departamento específico                                          | `200 OK`              | `id` (int) - ID del departamento                                  |
| **POST**        | /acex/departamentos                     | Crea un nuevo departamento.                                  | Departamento recién creado                                       | `201 Created`         | `Departamento` (JSON) - Datos del departamento                     |
| **PUT**         | /acex/departamentos/{id}                | Actualiza un departamento existente por ID.                   | Departamento actualizado                                         | `200 OK`              | `id` (int) - ID del departamento, `Departamento` (JSON) - Datos a actualizar |
| **DELETE**      | /acex/departamentos/{id}                | Elimina un departamento específico por ID.                    | Mensaje de confirmación de eliminación                            | `200 OK`              | `id` (int) - ID del departamento                                  |

# Endpoints de la API - EmpTransporteController

| **Método HTTP** | **Endpoint**                            | **Descripción**                                              | **Respuesta Exitosa**                                             | **Código de Estado**  | **Parámetros**                                                    |
|-----------------|-----------------------------------------|--------------------------------------------------------------|------------------------------------------------------------------|-----------------------|-------------------------------------------------------------------|
| **GET**         | /acex/transportes                       | Obtiene todos los transportes.                                | Lista de transportes                                             | `200 OK`              | Ninguno                                                           |
| **GET**         | /acex/transportes/{id}                  | Obtiene un transporte específico por ID.                       | Transporte específico                                            | `200 OK`              | `id` (int) - ID del transporte                                    |
| **POST**        | /acex/transportes                       | Crea un nuevo transporte.                                     | Transporte recién creado                                         | `201 Created`         | `EmpTransporte` (JSON) - Datos del transporte                      |
| **PUT**         | /acex/transportes/{id}                  | Actualiza un transporte existente por ID.                      | Transporte actualizado                                           | `200 OK`              | `id` (int) - ID del transporte, `EmpTransporte` (JSON) - Datos a actualizar |
| **DELETE**      | /acex/transportes/{id}                  | Elimina un transporte específico por ID.                       | Mensaje de confirmación de eliminación                            | `200 OK`              | `id` (int) - ID del transporte                                    |

# Endpoints de la API - FotoController

| **Método HTTP** | **Endpoint**                                | **Descripción**                                              | **Respuesta Exitosa**                                             | **Código de Estado**  | **Parámetros**                                                    |
|-----------------|---------------------------------------------|--------------------------------------------------------------|------------------------------------------------------------------|-----------------------|-------------------------------------------------------------------|
| **GET**         | /acex/fotos                                 | Obtiene todas las fotos.                                      | Lista de fotos                                                   | `200 OK`              | Ninguno                                                           |
| **GET**         | /acex/fotos/{id}                            | Obtiene una foto específica por ID.                           | Foto específica                                                  | `200 OK`              | `id` (int) - ID de la foto                                        |
| **GET**         | /acex/fotos/actividad/{idActividad}         | Obtiene fotos asociadas a una actividad.                      | Lista de fotos asociadas a la actividad                           | `200 OK`              | `idActividad` (int) - ID de la actividad                          |
| **POST**        | /acex/fotos                                 | Crea una nueva foto.                                          | Foto recién creada                                               | `201 Created`         | `Foto` (JSON) - Datos de la foto a crear                          |
| **PUT**         | /acex/fotos/{id}                            | Actualiza una foto existente por ID.                          | Foto actualizada                                                 | `200 OK`              | `id` (int) - ID de la foto, `Foto` (JSON) - Datos a actualizar    |
| **DELETE**      | /acex/fotos/{id}                            | Elimina una foto por ID.                                      | Foto eliminada                                                   | `200 OK`              | `id` (int) - ID de la foto                                        |
| **POST**        | /acex/fotos/{idActividad}/foto              | Guarda una foto asociada a una actividad.                     | Foto subida correctamente                                         | `201 Created`         | `idActividad` (int) - ID de la actividad, `descripcion` (String), `fichero` (MultipartFile) |
| **GET**         | /acex/fotos/{idActividad}/foto              | Obtiene una foto asociada a una actividad.                    | Foto específica asociada a la actividad                           | `200 OK`              | `idActividad` (int) - ID de la actividad, `id` (int) - ID de la foto |

# Endpoints de la API - GrupoController

| **Método HTTP** | **Endpoint**                                | **Descripción**                                              | **Respuesta Exitosa**                                             | **Código de Estado**  | **Parámetros**                                                    |
|-----------------|---------------------------------------------|--------------------------------------------------------------|------------------------------------------------------------------|-----------------------|-------------------------------------------------------------------|
| **GET**         | /acex/grupos                                | Obtiene todos los grupos.                                    | Lista de grupos                                                  | `200 OK`              | Ninguno                                                           |
| **GET**         | /acex/grupos/{id}                           | Obtiene un grupo específico por ID.                           | Grupo específico                                                  | `200 OK`              | `id` (int) - ID del grupo                                         |
| **POST**        | /acex/grupos                                | Crea un nuevo grupo.                                          | Grupo recién creado                                               | `201 Created`         | `Grupo` (JSON) - Datos del grupo a crear                          |
| **PUT**         | /acex/grupos/{id}                           | Actualiza un grupo existente por ID.                          | Grupo actualizado                                                 | `200 OK`              | `id` (int) - ID del grupo, `Grupo` (JSON) - Datos a actualizar    |
| **DELETE**      | /acex/grupos/{id}                           | Elimina un grupo por ID.                                      | Grupo eliminado                                                   | `200 OK`              | `id` (int) - ID del grupo                                         |

# Endpoints de la API - GrupoParticipanteController

| **Método HTTP** | **Endpoint**                                         | **Descripción**                                                     | **Respuesta Exitosa**                                            | **Código de Estado**   | **Parámetros**                                                   |
|-----------------|------------------------------------------------------|---------------------------------------------------------------------|------------------------------------------------------------------|------------------------|------------------------------------------------------------------|
| **GET**         | /acex/gruposParticipantes                            | Obtiene todos los grupos participantes.                             | Lista de grupos participantes                                    | `200 OK`               | Ninguno                                                          |
| **GET**         | /acex/gruposParticipantes/{id}                       | Obtiene un grupo participante específico por ID.                    | Grupo participante específico                                    | `200 OK`               | `id` (Integer) - ID del grupo participante                       |
| **GET**         | /acex/gruposParticipantes/actividad/{id}             | Obtiene grupos participantes por ID de actividad.                   | Lista de grupos participantes asociados a la actividad          | `200 OK`               | `id` (Integer) - ID de la actividad                              |
| **POST**        | /acex/gruposParticipantes                            | Crea un nuevo grupo participante.                                   | Grupo participante recién creado                                 | `201 Created`          | `GrupoParticipante` (JSON) - Datos del grupo participante a crear|
| **PUT**         | /acex/gruposParticipantes/{id}                       | Actualiza un grupo participante existente por ID.                   | Grupo participante actualizado                                   | `200 OK`               | `id` (Integer) - ID del grupo participante, `GrupoParticipante` (JSON) - Datos a actualizar |
| **DELETE**      | /acex/gruposParticipantes/{id}                       | Elimina un grupo participante por ID.                               | Grupo participante eliminado                                     | `200 OK`               | `id` (Integer) - ID del grupo participante                       |

# Endpoints de la API - ProfesorController

| **Método HTTP** | **Endpoint**                                         | **Descripción**                                                       | **Respuesta Exitosa**                                            | **Código de Estado**   | **Parámetros**                                                   |
|-----------------|------------------------------------------------------|-----------------------------------------------------------------------|------------------------------------------------------------------|------------------------|------------------------------------------------------------------|
| **GET**         | /acex/profesores                                     | Obtiene todos los profesores.                                         | Lista de todos los profesores                                     | `200 OK`               | Ninguno                                                          |
| **GET**         | /acex/profesores/{correo}                            | Obtiene un profesor específico por su correo.                         | Profesor con los detalles del correo especificado                | `200 OK`               | `correo` (String) - Correo del profesor                          |
| **POST**        | /acex/profesores                                     | Crea un nuevo profesor.                                               | Profesor recién creado                                           | `201 Created`          | `Profesor` (JSON) - Datos del profesor a crear                   |
| **PUT**         | /acex/profesores/{id}                                | Actualiza un profesor existente por ID.                               | Profesor actualizado                                             | `200 OK`               | `id` (String) - ID del profesor, `Profesor` (JSON) - Datos a actualizar |
| **DELETE**      | /acex/profesores/{id}                                | Elimina un profesor por ID.                                           | Profesor eliminado                                               | `200 OK`               | `id` (String) - ID del profesor                                  |
| **GET**         | /acex/profesores/inicio                              | Inicia sesión para un profesor con correo y contraseña.              | Profesor con detalles si la autenticación es exitosa             | `200 OK`               | `correo` (String), `password` (String) - Correo y contraseña     |
| **PUT**         | /acex/profesores/{correo}/foto                       | Guarda una foto para un profesor específico.                          | Mensaje de éxito indicando que la foto fue subida correctamente  | `200 OK`               | `correo` (String) - Correo del profesor, `fichero` (MultipartFile) - Foto del profesor |
| **GET**         | /acex/profesores/{correo}/foto                       | Obtiene la foto del profesor específico.                              | Foto del profesor en formato imagen                              | `200 OK`               | `correo` (String) - Correo del profesor                          |

# Endpoints de la API - ProfParticipanteController

| **Método HTTP** | **Endpoint**                                               | **Descripción**                                                         | **Respuesta Exitosa**                                             | **Código de Estado**   | **Parámetros**                                                   |
|-----------------|------------------------------------------------------------|-------------------------------------------------------------------------|-------------------------------------------------------------------|------------------------|------------------------------------------------------------------|
| **GET**         | /acex/profesoresParticipantes                               | Obtiene todos los participantes de profesores.                          | Lista de todos los participantes de profesores                      | `200 OK`               | Ninguno                                                          |
| **GET**         | /acex/profesoresParticipantes/{id}                          | Obtiene un participante de profesor por ID.                             | Detalles del participante de profesor con el ID especificado       | `200 OK`               | `id` (Integer) - ID del participante de profesor                 |
| **GET**         | /acex/profesoresParticipantes/actividad/{id}                | Obtiene los participantes de profesores para una actividad específica. | Lista de participantes de profesores para la actividad con el ID  | `200 OK`               | `id` (Integer) - ID de la actividad                              |
| **POST**        | /acex/profesoresParticipantes                               | Crea un nuevo participante de profesor.                                 | Participante de profesor creado                                    | `201 Created`          | `ProfParticipante` (JSON) - Datos del participante a crear       |
| **PUT**         | /acex/profesoresParticipantes/{id}                          | Actualiza un participante de profesor existente por ID.                 | Participante de profesor actualizado                               | `200 OK`               | `id` (Integer) - ID del participante, `ProfParticipante` (JSON) - Datos a actualizar |
| **DELETE**      | /acex/profesoresParticipantes/{id}                          | Elimina un participante de profesor por ID.                             | Participante de profesor eliminado                                 | `200 OK`               | `id` (Integer) - ID del participante de profesor                 |

# Endpoints de la API - ProfResponsableController

| **Método HTTP** | **Endpoint**                                                 | **Descripción**                                                       | **Respuesta Exitosa**                                          | **Código de Estado**   | **Parámetros**                                                   |
|-----------------|--------------------------------------------------------------|-----------------------------------------------------------------------|----------------------------------------------------------------|------------------------|------------------------------------------------------------------|
| **GET**         | /acex/profesoresResponsables                                  | Obtiene todos los responsables de profesores.                          | Lista de todos los responsables de profesores                    | `200 OK`               | Ninguno                                                          |
| **GET**         | /acex/profesoresResponsables/{id}                             | Obtiene un responsable de profesor por ID.                             | Detalles del responsable de profesor con el ID especificado     | `200 OK`               | `id` (Integer) - ID del responsable de profesor                   |
| **POST**        | /acex/profesoresResponsables                                  | Crea un nuevo responsable de profesor.                                 | Responsable de profesor creado                                  | `201 Created`          | `ProfResponsable` (JSON) - Datos del responsable a crear         |
| **PUT**         | /acex/profesoresResponsables/{id}                             | Actualiza un responsable de profesor existente por ID.                 | Responsable de profesor actualizado                             | `200 OK`               | `id` (Integer) - ID del responsable, `ProfResponsable` (JSON) - Datos a actualizar |
| **DELETE**      | /acex/profesoresResponsables/{id}                             | Elimina un responsable de profesor por ID.                             | Responsable de profesor eliminado                               | `200 OK`               | `id` (Integer) - ID del responsable de profesor                   |

# Endpoints de la API - PuntoInteresController

| **Método HTTP** | **Endpoint**                                           | **Descripción**                                                       | **Respuesta Exitosa**                                         | **Código de Estado**   | **Parámetros**                                                |
|-----------------|--------------------------------------------------------|-----------------------------------------------------------------------|--------------------------------------------------------------|------------------------|---------------------------------------------------------------|
| **GET**         | /acex/puntosinteres                                    | Obtiene todos los puntos de interés.                                  | Lista de todos los puntos de interés                           | `200 OK`               | Ninguno                                                       |
| **GET**         | /acex/puntosinteres/{id}                               | Obtiene un punto de interés por ID.                                   | Detalles del punto de interés con el ID especificado          | `200 OK`               | `id` (Integer) - ID del punto de interés                       |
| **POST**        | /acex/puntosinteres                                    | Crea un nuevo punto de interés.                                       | Punto de interés creado                                        | `201 Created`          | `PuntoInteres` (JSON) - Datos del punto de interés a crear    |
| **PUT**         | /acex/puntosinteres/{id}                               | Actualiza un punto de interés existente por ID.                       | Punto de interés actualizado                                   | `200 OK`               | `id` (Integer) - ID del punto de interés, `PuntoInteres` (JSON) - Datos a actualizar |
| **DELETE**      | /acex/puntosinteres/{id}                               | Elimina un punto de interés por ID.                                   | Punto de interés eliminado                                     | `200 OK`               | `id` (Integer) - ID del punto de interés                       |

# Servicios🛠️

Este apartado describe el servicio encargado de manejar la subida de ficheros dentro de la API. Se utiliza la clase `FileUploadUtil`, la cual contiene un método para guardar ficheros en el servidor.

## Clase `FileUploadUtil` - Subida de Ficheros

Esta clase se encarga de manejar la subida de ficheros a un directorio específico en el servidor. El siguiente código muestra la implementación de la clase `FileUploadUtil` y cómo usarla para guardar un fichero.

### Código de la clase `FileUploadUtil`

```java

public class FileUploadUtil {

    public static void guardarFichero(String directorioSubida, String nombreFichero,
    MultipartFile multipartFile) throws IOException {
        Path rutaSubida = Paths.get(directorioSubida);

        // Verifica si el directorio de subida existe, si no, lo crea
        if (!Files.exists(rutaSubida)) {
            try {
                Files.createDirectories(rutaSubida);
            } catch (IOException e) {
                System.out.println("Error al crear el directorio de subida " + e.getMessage());
            }
        }

        // Guarda el archivo en el directorio especificado
        try (InputStream input = multipartFile.getInputStream()) {
            Path rutaDelFichero = rutaSubida.resolve(nombreFichero);
            Files.copy(input, rutaDelFichero, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.out.println("No se pudo guardar el fichero: " + nombreFichero + " ERROR: " + e.getMessage());
        }
    }
}
```
# Conclusión

En ProjectStore hemos implementado varias funcionalidades para la gestión de ficheros y la manipulación de recursos en una aplicación Spring Boot. A través de la creación de controladores y servicios, hemos logrado estructurar y desarrollar una API que nos permite manejar todos los datos
de los proyectos de IES MIGUEL HERRERO, y como siempre hay margen de mejora pero estaremos encantados de traerles nuevas mejoras
contacten con nosotros para continuar ayudandoles. Muchas gracias😄🔥.
de los proyectos de IES MIGUEL HERRERO, y como siempre hay margen de mejora pero estaremos encantados de traerles nuevas mejoras
contacten con nosotros para continuar ayudandoles. Muchas gracias.

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
    ├── controller      # Controladores (Contiene los controladores que definen los endpoints de la API.)
    ├── model           # Clases de modelo y enums (Incluye las entidades que representan tablas de la base de datos.)
    ├── repository      # Repositorios (Define las interfaces para interactuar con la base de datos mediante JPA.)
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

Los repositorios en esta API representan la capa de acceso a datos. 

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

### Ejemplo de Repositorio: `AlumnoRepository`

El repositorio `AlumnoRepository` permite acceder y manipular los datos de los alumnos. 

- `findAll()`: Obtiene todos los alumnos.
- `findByDni(String dni)`: Busca un alumno por su DNI.
- `findByEmail(String email)`: Busca un alumno por su email.
- `findById(Long id)`: Busca un alumno por su ID.
- `save(Alumno alumno)`: Guarda o actualiza un alumno.
- `update(Alumno alumno)`: Actualiza un alumno.
- `delete(Alumno alumno)`: Elimina un alumno.

# Controladores (EndPoints)

En esta sección se detallan los controladores de la API, los cuales son responsables de manejar las solicitudes HTTP entrantes y devolver las respuestas adecuadas. Cada controlador está vinculado a una ruta específica y proporciona diferentes operaciones sobre los recursos de la aplicación.

A continuación, se describe el controlador `AlumnoController`, presentando un resumen  de todos los endpoints disponibles en este controlador.

# Endpoints de ProjectStore - AlumnoController

| **Método HTTP** | **Endpoint**              | **Descripción**                                      | **Respuesta Exitosa** | **Código de Estado** | **Parámetros**                       |
|-----------------|---------------------------|------------------------------------------------------|-----------------------|----------------------|--------------------------------------|
| `GET`           | `/alumnos`                | Obtiene todos los alumnos.                           | Lista de alumnos       | `200 OK`             | Ninguno                              |
| `GET`           | `/alumnos/dni/{dni}`      | Obtiene un alumno por su DNI.                        | Detalles del alumno    | `200 OK`             | `dni` (string)                       |
| `GET`           | `/alumnos/email/{email}`  | Obtiene un alumno por su correo electrónico.         | Detalles del alumno    | `200 OK`             | `email` (string)                     |
| `POST`          | `/alumnos`                | Crea un nuevo alumno.                                | Detalles del alumno creado | `201 Created`       | Cuerpo JSON con los datos del alumno |
| `PUT`           | `/alumnos/{idalumno}`     | Actualiza un alumno existente.                       | Alumno actualizado     | `200 OK`             | `idalumno` (string), Cuerpo JSON con los datos a actualizar |
| `DELETE`        | `/alumnos/{idAlumno}`     | Elimina un alumno por su ID. 

# Endpoints de ProjectStore - CicloController

| **Método HTTP** | **Endpoint**                  | **Descripción**                                    | **Respuesta Exitosa** | **Código de Estado** | **Parámetros**                          |
|-----------------|-------------------------------|----------------------------------------------------|-----------------------|----------------------|-----------------------------------------|
| `GET`           | `/ciclos`                     | Obtiene todos los ciclos.                          | Lista de ciclos        | `200 OK`             | Ninguno                                 |
| `GET`           | `/ciclos/codciclo/{codciclo}`  | Obtiene un ciclo por su código.                    | Detalles del ciclo     | `200 OK`             | `codciclo` (string)                     |
| `GET`           | `/ciclos/etapa/{etapa}`        | Obtiene ciclos según su etapa.                     | Lista de ciclos        | `200 OK`             | `etapa` (enum de tipo `Etapa`)          |

# Endpoints de ProjectStore - EvaluaController

| **Método HTTP** | **Endpoint**                       | **Descripción**                                     | **Respuesta Exitosa** | **Código de Estado** | **Parámetros**                           |
|-----------------|------------------------------------|-----------------------------------------------------|-----------------------|----------------------|------------------------------------------|
| `GET`           | `/evaluan`                         | Obtiene todas las evaluaciones.                      | Lista de evaluaciones  | `200 OK`             | Ninguno                                  |
| `GET`           | `/evaluan/id/{id}`                 | Obtiene una evaluación por su ID.                    | Detalles de la evaluación | `200 OK`             | `id` (int)                               |
| `GET`           | `/evaluan/profesor/{profesor}`     | Obtiene evaluaciones asociadas a un profesor.        | Lista de evaluaciones  | `200 OK`             | `profesor` (string)                      |
| `GET`           | `/evaluan/proyecto/{proyecto}`     | Obtiene evaluaciones asociadas a un proyecto.        | Lista de evaluaciones  | `200 OK`             | `proyecto` (int)                         |
| `POST`          | `/evaluan`                         | Crea una nueva evaluación.                          | Detalles de la evaluación creada | `201 Created`   | Cuerpo JSON con los datos de la evaluación |
| `PUT`           | `/evaluan/{id}`                    | Actualiza una evaluación existente.                 | Evaluación actualizada | `200 OK`             | `id` (int), Cuerpo JSON con los datos a actualizar |
| `DELETE`        | `/evaluan/{id}`                    | Elimina una evaluación por su ID.                   | Sin contenido           | `204 No Content`     | `id` (int)                               |

# Endpoints de ProjectStore - ProfesorController

| **Método HTTP** | **Endpoint**                        | **Descripción**                                      | **Respuesta Exitosa**  | **Código de Estado** | **Parámetros**                               |
|-----------------|-------------------------------------|------------------------------------------------------|------------------------|----------------------|----------------------------------------------|
| `GET`           | `/profesores`                       | Obtiene todos los profesores.                        | Lista de profesores     | `200 OK`             | Ninguno                                      |
| `GET`           | `/profesores/idprofesor/{idProfesor}`| Obtiene un profesor por su ID.                       | Detalles del profesor   | `200 OK`             | `idProfesor` (string)                        |
| `GET`           | `/profesores/admin/{admin}`         | Obtiene profesores según si son administradores.     | Lista de profesores     | `200 OK`             | `admin` (boolean)                            |
| `GET`           | `/profesores/nombre/{nombre}`       | Obtiene un profesor por su nombre y apellidos.       | Detalles del profesor   | `200 OK`             | `nombre` (string), `apellidos` (string)      |
| `GET`           | `/profesores/email/{email}`         | Obtiene un profesor por su correo electrónico y contraseña. | Detalles del profesor   | `200 OK`             | `email` (string), `contraseña` (string)      |
| `POST`          | `/profesores`                       | Crea un nuevo profesor.                              | Detalles del profesor creado | `201 Created`   | Cuerpo JSON con los datos del profesor       |
| `PUT`           | `/profesores/{idprofesor}`          | Actualiza un profesor existente.                     | Profesor actualizado    | `200 OK`             | `idprofesor` (string), Cuerpo JSON con los datos a actualizar |
| `DELETE`        | `/profesores/{idProfesor}`          | Elimina un profesor por su ID.                       | Sin contenido           | `204 No Content`     | `idProfesor` (string)                        |

# Endpoints de ProjectStore - ProyectoController

| **Método HTTP** | **Endpoint**                             | **Descripción**                                       | **Respuesta Exitosa**  | **Código de Estado** | **Parámetros**                               |
|-----------------|------------------------------------------|-------------------------------------------------------|------------------------|----------------------|----------------------------------------------|
| `GET`           | `/proyectos`                             | Obtiene todos los proyectos.                          | Lista de proyectos      | `200 OK`             | Ninguno                                      |
| `GET`           | `/proyectos/tipo/{tipo}`                 | Obtiene proyectos por tipo.                           | Lista de proyectos      | `200 OK`             | `tipo` (string)                              |
| `GET`           | `/proyectos/nombre/{nombre}`             | Obtiene un proyecto por su nombre.                    | Detalles del proyecto   | `200 OK`             | `nombre` (string)                            |
| `GET`           | `/proyectos/idproyecto/{idProyecto}`     | Obtiene un proyecto por su ID.                        | Detalles del proyecto   | `200 OK`             | `idProyecto` (int)                           |
| `POST`          | `/proyectos`                             | Crea un nuevo proyecto.                               | Detalles del proyecto creado | `201 Created`     | Cuerpo JSON con los datos del proyecto       |
| `PUT`           | `/proyectos/{proyecto}`                  | Actualiza un proyecto existente.                      | Proyecto actualizado    | `200 OK`             | `proyecto` (int), Cuerpo JSON con los datos a actualizar |
| `DELETE`        | `/proyectos/{idProyecto}`                | Elimina un proyecto por su ID.                        | Sin contenido           | `204 No Content`     | `idProyecto` (int)                           |
| `PUT`           | `/proyectos/ficheros`                    | Sube un archivo (imagen, PDF, ZIP) asociado a un proyecto. | Confirmación de archivo subido | `200 OK`         | `idProyecto` (int), `fichero` (archivo)      |
| `GET`           | `/proyectos/ficheros`                    | Obtiene un archivo asociado a un proyecto (logo, memoria, archivos). | Archivo solicitado       | `200 OK`             | `idProyecto` (int), `tipo` (string: logo, memoria, archivos) |


# Endpoints de ProjectStore - RealizaController

| **Método HTTP** | **Endpoint**                             | **Descripción**                                       | **Respuesta Exitosa**  | **Código de Estado** | **Parámetros**                               |
|-----------------|------------------------------------------|-------------------------------------------------------|------------------------|----------------------|----------------------------------------------|
| `GET`           | `/realizan`                              | Obtiene todas las realizaciones.                       | Lista de realizaciones  | `200 OK`             | Ninguno                                      |
| `GET`           | `/realizan/id/{id}`                      | Obtiene una realización por su ID.                     | Detalles de la realización | `200 OK`             | `id` (int)                                   |
| `GET`           | `/realizan/alumno/{alumno}`              | Obtiene la realización por el nombre del alumno.       | Detalles de la realización | `200 OK`             | `alumno` (string)                            |
| `GET`           | `/realizan/proyecto/{proyecto}`          | Obtiene la realización por el ID del proyecto.         | Detalles de la realización | `200 OK`             | `proyecto` (int)                             |
| `POST`          | `/realizan`                              | Crea una nueva realización.                           | Detalles de la realización creada | `201 Created`     | Cuerpo JSON con los datos de la realización   |
| `PUT`           | `/realizan/{id}`                         | Actualiza una realización existente.                  | Realización actualizada | `200 OK`             | `id` (int), Cuerpo JSON con los datos a actualizar |
| `DELETE`        | `/realizan/{realiza}`                    | Elimina una realización por su ID.                    | Sin contenido           | `204 No Content`     | `realiza` (realización)                      |

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

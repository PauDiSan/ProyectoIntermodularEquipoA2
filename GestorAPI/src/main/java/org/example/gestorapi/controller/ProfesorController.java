package org.example.gestorapi.controller;


import org.apache.commons.io.FilenameUtils;
import org.example.gestorapi.model.Actividad;
import org.example.gestorapi.model.Profesor;
import org.example.gestorapi.service.files.FileUploadUtil;
import org.example.gestorapi.service.impl.ProfesorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/acex")
public class ProfesorController {
    @Autowired
    private ProfesorServiceImpl profesorService;


    @GetMapping("/profesores")
    public ResponseEntity<?> getProfesores() {
        List<Profesor> profesores = profesorService.findAll();
        if (profesores.isEmpty()) {
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(profesores);
        }
    }

    @GetMapping("/profesores/{correo}")
    public ResponseEntity<?> getProfesorById(@PathVariable String correo) {
        Profesor profesor = profesorService.findByEmail(correo);
        if (profesor == null) {
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(profesor);
        }
    }

    @PostMapping("/profesores")
    public ResponseEntity<Profesor> createProfesor(@RequestBody Profesor profesor) {
        Profesor nuevo = profesorService.guardar(profesor);
        if (nuevo == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }else{
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
        }
    }

    @PutMapping("/profesores/{id}")
    public ResponseEntity<?> updateProfesor(@PathVariable String id, @RequestBody Profesor profesor) {
        Profesor actualizado = profesorService.findById(id);
        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(profesorService.actualizar(profesor,id));
        }
    }

    @DeleteMapping("/profesores/{id}")
    public ResponseEntity<Profesor> deleteProfesor(@PathVariable String id) {
        Profesor eliminado = profesorService.findById(id);
        if (eliminado == null) {
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(profesorService.eliminar(id));
        }
    }

    @GetMapping("/profesores/inicio")
    public ResponseEntity<?> inicioSesion(@RequestParam String correo, @RequestParam String password) {
        Profesor profesor = profesorService.inicioSesion(correo, password);
        if (profesor == null) {
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(profesor);
        }
    }

    @PutMapping("/profesores/{correo}/foto")
    public ResponseEntity<String> guardarFotoProfesor(@PathVariable("correo") String correo,
                                                          @RequestParam("fichero") MultipartFile multipartFile) {

        // Verificación de que el archivo no está vacío
        if (multipartFile.isEmpty()) {
            return ResponseEntity.badRequest().body("No se ha seleccionado un archivo.");
        }

        // Limpia el nombre del archivo
        String nombreArchivo = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));

        // Directorio donde se almacenará el archivo
        String uploadDir = "profesores/"+ correo + "/foto/";

        // Crear el directorio si no existe
        File directory = new File(uploadDir);
        //Se borra el directorio y se vuelve a crear con la foto nueva
        directory.delete();
        directory.mkdirs();



        // Obtener la extensión del archivo
        String extension = FilenameUtils.getExtension(nombreArchivo).toLowerCase();

        // Validar si el archivo es una imagen o un PDF
        boolean esImagen = extension.equals("jpg") || extension.equals("jpeg") || extension.equals("png");
        boolean esPDF = extension.equals("pdf");
        boolean esZip = extension.equals("zip");

        if (!esImagen && !esPDF && !esZip ) {
            return ResponseEntity.badRequest().body("El archivo debe ser una imagen JPG, JPEG, PNG, un PDF o un Zip.");
        }

        // Buscar el proyecto por ID
        Profesor profesor = profesorService.findByEmail(correo);

        if (profesor == null) {
            return ResponseEntity.badRequest().body("La actividad no existe.");
        }

        try {
            // Guardar el archivo usando un método utilitario (asegúrate de que esta clase esté implementada)
            FileUploadUtil.guardarFichero(uploadDir, nombreArchivo, multipartFile);

            // Actualizar el proyecto según el tipo de archivo
             if (esImagen) {
                profesor.setUrlFoto(nombreArchivo);
                profesorService.guardar(profesor);

                return ResponseEntity.ok("Archivo subido correctamente para el profesor con correo " + profesor.getCorreo());
            }else{
                return ResponseEntity.status(500).body("Formato fichero incorrecto no es pdf");
            }


        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error al subir el archivo: " + e.getMessage());
        }
    }
    @GetMapping("/profesores/{correo}/foto")
    public ResponseEntity<Resource> getFotoProfesor(@PathVariable("correo") String correo) {
        // Buscar el proyecto por ID
        Profesor profesor = profesorService.findByEmail(correo);

        if (profesor == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        // Determinar qué archivo se quiere obtener
        String nombreArchivo = profesor.getUrlFoto();

        if (nombreArchivo == null || nombreArchivo.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        try {
            // Ruta al archivo almacenado
            Path filePath = Paths.get("profesores/"+ correo + "/foto/").resolve(nombreArchivo);
            Resource resource = new UrlResource(filePath.toUri());

            // Verificar si el archivo existe y es legible
            if (!resource.exists() || !resource.isReadable()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            // Configurar los headers para descargar el archivo
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}

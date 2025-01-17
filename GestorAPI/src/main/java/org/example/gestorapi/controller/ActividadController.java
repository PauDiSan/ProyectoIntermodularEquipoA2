package org.example.gestorapi.controller;

import org.apache.commons.io.FilenameUtils;
import org.example.gestorapi.model.Actividad;
import org.example.gestorapi.service.ActividadService;
import org.example.gestorapi.service.files.FileUploadUtil;
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
public class ActividadController {

    @Autowired
    private ActividadService actividadService;

    @GetMapping("/actividades")
    public ResponseEntity<?> getActividades() {
        List<Actividad> actividades = actividadService.findAll();
        if (actividades.isEmpty()) {
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(actividades);
        }
    }

    @GetMapping("/actividades/{id}")
    public ResponseEntity<?> getActividadById(@PathVariable int id) {
        Actividad actividad = actividadService.findById(id);
        if(actividad == null) {
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(actividad);
        }
    }


    @PostMapping("/actividades")
    public ResponseEntity<?> createActividad(@RequestBody Actividad actividad) {
        Actividad guardado = actividadService.guardar(actividad);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }

    @PutMapping("/actividades/{id}")
    public ResponseEntity<?> updateActividad(@PathVariable int id, @RequestBody Actividad editar) {
        Actividad actividad = actividadService.findById(id);
        if(actividad == null) {
            return ResponseEntity.notFound().build();

        }else{
            return ResponseEntity.ok(actividadService.actualizar(editar, id));
        }
    }

    @DeleteMapping("/actividades/{id}")
    public ResponseEntity<?> deleteActividad(@PathVariable Integer id) {
        Actividad eliminada = actividadService.findById(id);
        if(eliminada == null) {
            return ResponseEntity.notFound().build();

        }else{
            return ResponseEntity.ok(actividadService.eliminar(id));
        }

    }


    @PutMapping("/actividades/{id}/folleto")
    public ResponseEntity<String> guardarFolletoActividad(@PathVariable("id") int id,
                                               @RequestParam("fichero") MultipartFile multipartFile) {

        // Verificación de que el archivo no está vacío
        if (multipartFile.isEmpty()) {
            return ResponseEntity.badRequest().body("No se ha seleccionado un archivo.");
        }

        // Limpia el nombre del archivo
        String nombreArchivo = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));

        // Buscar el proyecto por ID
        Actividad actividad = actividadService.findById(id);
        if(actividad != null) {


        // Directorio donde se almacenará el archivo
        String uploadDir = "actividades/"+ id+"_" + actividad.getTitulo().replaceAll(" ","_") + "/folletos/";

        // Crear el directorio si no existe
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Obtener la extensión del archivo
        String extension = FilenameUtils.getExtension(nombreArchivo).toLowerCase();

        // Validar si el archivo es una imagen o un PDF
        boolean esImagen = extension.equals("jpg") || extension.equals("jpeg") || extension.equals("png");
        boolean esPDF = extension.equals("pdf");
        boolean esZip = extension.equals("zip");

        if (!esImagen && !esPDF && !esZip ) {
            return ResponseEntity.badRequest().body("El archivo debe ser una imagen JPG, JPEG, PNG, un PDF o un Zip.");
        }




        try {
            // Guardar el archivo usando un método utilitario (asegúrate de que esta clase esté implementada)
            FileUploadUtil.guardarFichero(uploadDir, nombreArchivo, multipartFile);

            // Actualizar el proyecto según el tipo de archivo
            // if (esImagen) {

             if (esPDF) {
                actividad.setUrlFolleto(nombreArchivo);
            //} else if (esZip) {

                 // Guardar los cambios en la base de datos
                 actividadService.guardar(actividad);

                 return ResponseEntity.ok("Archivo subido correctamente para la actividad " + actividad.getTitulo() + " con ID " + id);
            }else{
                 return ResponseEntity.status(500).body("Formato fichero incorrecto no es pdf");
             }


                } catch (IOException e) {
                     return ResponseEntity.status(500).body("Error al subir el archivo: " + e.getMessage());
                }
            }
        return ResponseEntity.status(500).body("Error al subir el archivo");
    }
    @GetMapping("/actividades/{id}/folleto")
    public ResponseEntity<Resource> getFolletoActividad(@PathVariable("id") int id) {
        // Buscar el proyecto por ID
        Actividad actividad = actividadService.findById(id);

        if (actividad == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        // Determinar qué archivo se quiere obtener
        String nombreArchivo = actividad.getUrlFolleto();

        if (nombreArchivo == null || nombreArchivo.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        try {
            // Ruta al archivo almacenado
            Path filePath = Paths.get("actividades/"+ id +"_"+actividad.getTitulo().replaceAll(" ","_") + "/folletos/").resolve(nombreArchivo);
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

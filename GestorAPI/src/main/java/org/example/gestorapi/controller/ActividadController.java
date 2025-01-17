package org.example.gestorapi.controller;

import org.apache.commons.io.FilenameUtils;
import org.example.gestorapi.model.Actividad;
import org.example.gestorapi.service.ActividadService;
import org.example.gestorapi.service.files.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
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

    @GetMapping("/actividades/excel")
    public ResponseEntity<Resource> downloadExcel(@RequestParam("actividad") int actividad) {
        try {
            actividadService.excel(actividadService.findById(actividad));
            String filename = "autorizacion.xlsx";
            String tmpdir = System.getProperty("java.io.tmpdir");
            // Definir la ruta del archivo
            Path filePath = Paths.get(tmpdir + filename);
            File file = filePath.toFile();

            // Verificar si el archivo existe
            if (!file.exists()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            // Crear un InputStreamResource a partir del archivo
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamResource resource = new InputStreamResource(fileInputStream);

            // Retornar el archivo con los encabezados adecuados
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename) // Hacerlo descargable
                    .contentType(MediaType.APPLICATION_OCTET_STREAM) // Tipo de contenido binario
                    .body(resource); // Usar InputStreamResource para la respuesta

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // Manejo de error
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

        // Directorio donde se almacenará el archivo
        String uploadDir = "actividades/"+ id + "/folletos/";

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

        // Buscar el proyecto por ID
        Actividad actividad = actividadService.findById(id);

        if (actividad == null) {
            return ResponseEntity.badRequest().body("La actividad no existe.");
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
            Path filePath = Paths.get("actividades/"+ id + "/folletos/").resolve(nombreArchivo);
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

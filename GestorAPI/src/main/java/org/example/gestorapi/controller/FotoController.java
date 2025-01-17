package org.example.gestorapi.controller;

import org.apache.commons.io.FilenameUtils;
import org.example.gestorapi.model.Actividad;
import org.example.gestorapi.model.Foto;
import org.example.gestorapi.model.Profesor;
import org.example.gestorapi.repository.FotoRepository;
import org.example.gestorapi.service.ActividadService;
import org.example.gestorapi.service.FotoService;
import org.example.gestorapi.service.ProfesorService;
import org.example.gestorapi.service.files.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/acex")
public class FotoController {

    @Autowired
    private FotoService fotoService;
    @Autowired
    private ActividadService actividadService;
    @Autowired
    private FotoRepository fotoRepository;

    @GetMapping("/fotos")
    public ResponseEntity<?> getAllFoto(){
        List<Foto> fotos = fotoService.findAll();
        if(fotos.isEmpty())
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok(fotos);
    }
    @GetMapping("/fotos/{id}")
    public ResponseEntity<?> getFotoById(@PathVariable int id){
        Foto foto = fotoService.findById(id);
        if(foto == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(foto);
    }
    @PostMapping("/fotos")
    public ResponseEntity<Foto> nuevaFoto(@RequestBody Foto foto){
        Foto guardado= fotoService.guardar(foto);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }
    @PutMapping("/fotos/{id}")
    public ResponseEntity<?> updateFoto(@PathVariable int id, @RequestBody Foto foto){
        Foto f= fotoService.findById(id);
        if(foto == null)
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok(fotoService.actualizar(foto,id));

    }

    @DeleteMapping("/fotos/{id}")
    public ResponseEntity<?> deleteFoto(@PathVariable int id){
        Foto eliminado= fotoService.eliminar(id);
        if(eliminado == null){
            return ResponseEntity.notFound().build();
        }else{
            File fichero = new File("actividades/"+ eliminado.getActividad().getId()+"_"+
                    eliminado.getActividad().getTitulo().replaceAll(" ","_") + "/fotos/"+eliminado.getUrlFoto());
            fichero.delete();
            return ResponseEntity.ok(eliminado);
        }


    }

    @PostMapping("/fotos/{idActividad}/foto")
    public ResponseEntity<Foto> guardarFotoActividad(@PathVariable("idActividad") int idActividad,
                                                          @RequestParam("descripcion") String descripcion,
                                                          @RequestParam("fichero") MultipartFile multipartFile) {

        // Verificación de que el archivo no está vacío
        if (multipartFile.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        // Limpia el nombre del archivo
        String nombreArchivo = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));


        Actividad actividad = actividadService.findById(idActividad);
        if(actividad != null) {


            // Directorio donde se almacenará el archivo
            String uploadDir = "actividades/" + idActividad + "_" + actividad.getTitulo().replaceAll(" ","_") + "/fotos/";

            // Crear el directorio si no existe
            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Obtener la extensión del archivo
            String extension = FilenameUtils.getExtension(nombreArchivo).toLowerCase();

            // Validar si el archivo es una imagen o un PDF
            boolean esImagen = extension.equals("jpg") || extension.equals("jpeg") || extension.equals("png");

            if (!esImagen) {
                return ResponseEntity.badRequest().build();
            }

            try {
                // Guardar el archivo usando un método utilitario (asegúrate de que esta clase esté implementada)
                FileUploadUtil.guardarFichero(uploadDir, nombreArchivo, multipartFile);
                Foto foto = new Foto();
                if (actividadService.findById(idActividad) != null) {
                    // Actualizar el proyecto según el tipo de archivo

                    foto.setUrlFoto(nombreArchivo);
                    foto.setDescripcion(descripcion);
                    foto.setActividad(actividadService.findById(idActividad));
                    fotoService.guardar(foto);
                    return ResponseEntity.status(HttpStatus.CREATED).body(foto);

                }
                return ResponseEntity.status(500).build();

            } catch (IOException e) {
                return ResponseEntity.status(500).build();
            }
        }else{
            return ResponseEntity.status(500).build();
        }
    }
    @GetMapping("/fotos/{idActividad}/foto")
    public ResponseEntity<Resource> getFotoActividad(@PathVariable("idActividad") Integer idActividad,
                                                        @RequestParam("id") Integer id) {
        // Buscar el proyecto por ID
        Foto foto = fotoService.findById(id);
        if (foto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        // Determinar qué archivo se quiere obtener
        String nombreArchivo = foto.getUrlFoto();

        if (nombreArchivo == null || nombreArchivo.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        try {
            // Ruta al archivo almacenado
            Path filePath = Paths.get("actividades/"+ idActividad +"_" + foto.getActividad().getTitulo().replaceAll(" ","_") + "/fotos/").resolve(nombreArchivo);
            Resource resource = new UrlResource(filePath.toUri());
            // Verificar si el archivo existe y es legible
            if (!resource.exists() || !resource.isReadable()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            // Configurar los headers para descargar el archivo
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}

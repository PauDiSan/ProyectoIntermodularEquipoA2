package org.example.gestorapi.controller;

import org.apache.commons.io.FilenameUtils;
import org.example.gestorapi.model.Actividad;
import org.example.gestorapi.model.Contrato;
import org.example.gestorapi.model.Foto;
import org.example.gestorapi.service.ActividadService;
import org.example.gestorapi.service.ContratoService;
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
public class ContratoController {

    @Autowired
    private ContratoService contratoService;
    @Autowired
    private ActividadService actividadService;

    @GetMapping("/contratos")
    public ResponseEntity<?> getContratoes() {
        List<Contrato> contratos = contratoService.findAll();
        if (contratos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(contratos);
        }
    }

    @GetMapping("/contratos/{id}")
    public ResponseEntity<?> getContratoById(@PathVariable int id) {
        Contrato contrato = contratoService.findById(id);
        if(contrato == null) {
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(contrato);
        }
    }

    @GetMapping("contratos/actividad/{idActividad}")
    public ResponseEntity<?> getContratosActividad(@PathVariable int idActividad) {
        List<Contrato> contratos = contratoService.contratosByActividadId(idActividad);
        if (contratos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(contratos);
        }
    }


    @PostMapping("/contratos")
    public ResponseEntity<?> createContrato(@RequestBody Contrato contrato) {
        Contrato guardado = contratoService.guardar(contrato);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }

    @PutMapping("/contratos/{id}")
    public ResponseEntity<?> updateContrato(@PathVariable int id, @RequestBody Contrato editar) {
        Contrato contrato = contratoService.findById(id);
        if(contrato == null) {
            return ResponseEntity.notFound().build();

        }else{
            return ResponseEntity.ok(contratoService.actualizar(editar, id));
        }
    }

    @DeleteMapping("/contratos/{id}")
    public ResponseEntity<?> deleteContrato(@PathVariable Integer id) {
        Contrato eliminada = contratoService.findById(id);
        if(eliminada == null) {
            return ResponseEntity.notFound().build();

        }else{
            return ResponseEntity.ok(contratoService.eliminar(id));
        }

    }

    @PutMapping("/contratos/{idActividad}/fichero")
    public ResponseEntity<String> getContratoPresupuesto(@PathVariable("idActividad") int idActividad,
                                                         @RequestBody Contrato contrato,
                                                          @RequestParam("fichero") MultipartFile multipartFile,
                                                         @RequestParam("esPresupuesto") Boolean esPresupuesto) {

        // Verificación de que el archivo no está vacío
        if (multipartFile.isEmpty()) {
            return ResponseEntity.badRequest().body("No se ha seleccionado un archivo.");
        }
        Actividad actividad = actividadService.findById(idActividad);
        if(actividad != null) {

        // Limpia el nombre del archivo
        String nombreArchivo = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));

        // Directorio donde se almacenará el archivo
        String uploadDir = "actividades/"+ idActividad +"_" + actividad.getTitulo().replaceAll(" ","_")+ "/contratos/";

        // Crear el directorio si no existe
        File directory = new File(uploadDir);

        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Obtener la extensión del archivo
        String extension = FilenameUtils.getExtension(nombreArchivo).toLowerCase();

        // Validar si el archivo es una imagen o un PDF
        boolean esPDF = extension.equals("pdf");


        if (!esPDF) {
            return ResponseEntity.badRequest().body("El archivo debe un PDF");
        }

        if (contrato == null) {
            return ResponseEntity.badRequest().body("El contrato no existe.");
        }

        try {

            // Guardar el archivo usando un método utilitario (asegúrate de que esta clase esté implementada)
            FileUploadUtil.guardarFichero(uploadDir, nombreArchivo, multipartFile);

            // Actualizar el proyecto según el tipo de archivo
            // if (esImagen) {

            if(esPresupuesto){
                    contrato.setUrlPresupuesto(nombreArchivo);
            }else{
                contrato.setUrlFactura(nombreArchivo);
            }
            //} else if (esZip) {

            // Guardar los cambios en la base de datos
            contratoService.guardar(contrato);

            return ResponseEntity.ok("Archivo" + (contrato.getUrlPresupuesto()==null?contrato.getUrlFactura():contrato.getUrlPresupuesto()) + "subido correctamente para la actividad con id" + contrato.getActividad().getId());

        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error al subir el archivo: " + e.getMessage());
        }
        }else{
            return ResponseEntity.notFound().build();
        }
    }



    @GetMapping("/contratos/{idActividad}/fichero")
    public ResponseEntity<Resource> getFolletoActividad(@PathVariable("idActividad") Integer idActividad,
                                                        @RequestParam("id") Integer id,
                                                        @RequestParam("esPresupuesto") Boolean esPresupuesto) {
        // Buscar el proyecto por ID
        Contrato contrato = contratoService.findById(id);

        if (contrato == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        // Determinar qué archivo se quiere obtener
        String nombreArchivo = null;
        if(esPresupuesto){
            nombreArchivo = contrato.getUrlPresupuesto();
        }else{
            nombreArchivo = contrato.getUrlFactura();
        }


        if (nombreArchivo == null || nombreArchivo.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        Actividad actividad = actividadService.findById(idActividad);
        if (actividad != null) {
        try {

            // Ruta al archivo almacenado
            Path filePath = Paths.get("actividades/"+ idActividad+"_"+ actividad.getTitulo().replaceAll(" ","_") + "/contratos/").resolve(nombreArchivo);
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
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }



}

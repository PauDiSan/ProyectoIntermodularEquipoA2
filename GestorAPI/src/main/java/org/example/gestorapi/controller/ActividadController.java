package org.example.gestorapi.controller;

import org.example.gestorapi.model.Actividad;
import org.example.gestorapi.service.ActividadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

}

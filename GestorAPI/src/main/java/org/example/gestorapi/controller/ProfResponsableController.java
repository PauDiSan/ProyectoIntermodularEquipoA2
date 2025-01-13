package org.example.gestorapi.controller;


import org.example.gestorapi.model.ProfResponsable;
import org.example.gestorapi.service.impl.ProfResponsableServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/acex")
public class ProfResponsableController {
    @Autowired
    private ProfResponsableServiceImpl profResponsableService;


    @GetMapping("/profesoresResponsables")
    public ResponseEntity<?> getProfResponsables() {
        List<ProfResponsable> profResponsables = profResponsableService.findAll();
        if (profResponsables.isEmpty()) {
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(profResponsables);
        }
    }

    @GetMapping("/profesoresResponsables/{id}")
    public ResponseEntity<?> getProfesorResponsableById(@PathVariable Integer id) {
        ProfResponsable profResponsable = profResponsableService.findById(id);
        if (profResponsable == null) {
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(profResponsable);
        }
    }

    @PostMapping("/profesoresResponsables")
    public ResponseEntity<ProfResponsable> createProfesorResponsable(@RequestBody ProfResponsable profResponsable) {
        ProfResponsable nuevo = profResponsableService.guardar(profResponsable);
        if (nuevo == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }else{
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
        }
    }

    @PutMapping("/profesoresResponsables/{id}")
    public ResponseEntity<?> updateProfesorResponsable(@PathVariable Integer id, @RequestBody ProfResponsable profResponsable) {
        ProfResponsable actualizado = profResponsableService.findById(id);
        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(profResponsableService.actualizar(profResponsable,id));
        }
    }

    @DeleteMapping("/profesoresResponsables/{id}")
    public ResponseEntity<ProfResponsable> deleteProfesorResponsable(@PathVariable Integer id) {
        ProfResponsable eliminado = profResponsableService.findById(id);
        if (eliminado == null) {
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(profResponsableService.eliminar(id));
        }
    }
}

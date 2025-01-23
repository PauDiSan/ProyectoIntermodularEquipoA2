package org.example.gestorapi.controller;


import org.example.gestorapi.model.ProfParticipante;
import org.example.gestorapi.service.impl.ProfParticipanteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/acex")
public class ProfParticipanteController {
    @Autowired
    private ProfParticipanteServiceImpl profParticipanteService;


    @GetMapping("/profesoresParticipantes")
    public ResponseEntity<?> getProfParticipantes() {
        List<ProfParticipante> profParticipantes = profParticipanteService.findAll();
        if (profParticipantes.isEmpty()) {
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(profParticipantes);
        }
    }

    @GetMapping("/profesoresParticipantes/{id}")
    public ResponseEntity<?> getProfesorParticipanteById(@PathVariable Integer id) {
        ProfParticipante profParticipante = profParticipanteService.findById(id);
        if (profParticipante == null) {
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(profParticipante);
        }
    }

    @GetMapping("/profesoresParticipantes/actividad/{id}")
    public ResponseEntity<?> getProfesorParticipanteActividads(@PathVariable Integer id) {
        List<ProfParticipante> profParticipantes = profParticipanteService.findProfParticipanteByActividadId(id);
        if (profParticipantes.isEmpty()) {
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(profParticipantes);
        }
    }

    @PostMapping("/profesoresParticipantes")
    public ResponseEntity<ProfParticipante> createProfesorParticipante(@RequestBody ProfParticipante profParticipante) {
        ProfParticipante nuevo = profParticipanteService.guardar(profParticipante);
        if (nuevo == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }else{
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
        }
    }

    @PutMapping("/profesoresParticipantes/{id}")
    public ResponseEntity<?> updateProfesorParticipante(@PathVariable Integer id, @RequestBody ProfParticipante profParticipante) {
        ProfParticipante actualizado = profParticipanteService.findById(id);
        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(profParticipanteService.actualizar(profParticipante,id));
        }
    }

    @DeleteMapping("/profesoresParticipantes/{id}")
    public ResponseEntity<ProfParticipante> deleteProfesorParticipante(@PathVariable Integer id) {
        ProfParticipante eliminado = profParticipanteService.findById(id);
        if (eliminado == null) {
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(profParticipanteService.eliminar(id));
        }
    }

}

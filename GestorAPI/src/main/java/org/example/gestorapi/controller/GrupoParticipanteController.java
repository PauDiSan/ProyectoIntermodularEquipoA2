package org.example.gestorapi.controller;

import org.example.gestorapi.model.GrupoParticipante;
import org.example.gestorapi.service.impl.GrupoParticipanteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/acex")
public class GrupoParticipanteController {
    @Autowired
    private GrupoParticipanteServiceImpl grupoParticipanteService;

    @GetMapping("/gruposParticipantes")
    public ResponseEntity<?> getGruposParticipantes() {
        List<GrupoParticipante> gruposParticipantes = grupoParticipanteService.findAll();
        if (gruposParticipantes.isEmpty()) {
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(gruposParticipantes);
        }
    }

    @GetMapping("/gruposParticipantes/{id}")
    public ResponseEntity<?> getGrupoParticipanteById(@PathVariable Integer id) {
        GrupoParticipante grupoParticipante = grupoParticipanteService.findById(id);
        if (grupoParticipante == null) {
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(grupoParticipante);
        }
    }

    @GetMapping("/gruposParticipantes/actividad/{id}")
    public ResponseEntity<?> getGruposParticipantes(@PathVariable Integer id) {
        List<GrupoParticipante> gruposParticipantes = grupoParticipanteService.findGrupoParticipantesByActividades_Id(id);
        if (gruposParticipantes.isEmpty()) {
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(gruposParticipantes);
        }
    }

    @PostMapping("/gruposParticipantes")
    public ResponseEntity<GrupoParticipante> createGrupoParticipante(@RequestBody GrupoParticipante grupoParticipante) {
        GrupoParticipante nuevo = grupoParticipanteService.guardar(grupoParticipante);
        if (nuevo == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }else{
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
        }
    }

    @PutMapping("/gruposParticipantes/{id}")
    public ResponseEntity<?> updateGrupoParticipante(@PathVariable Integer id, @RequestBody GrupoParticipante grupoParticipante) {
        GrupoParticipante actualizado = grupoParticipanteService.findById(id);
        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(grupoParticipanteService.actualizar(grupoParticipante,id));
        }
    }

    @DeleteMapping("/gruposParticipantes/{id}")
    public ResponseEntity<GrupoParticipante> deleteGrupoParticipante(@PathVariable Integer id) {
        GrupoParticipante eliminado = grupoParticipanteService.findById(id);
        if (eliminado == null) {
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(grupoParticipanteService.eliminar(id));
        }
    }
}

package org.example.gestorapi.controller;

import org.example.gestorapi.model.Curso;
import org.example.gestorapi.model.PuntoInteres;
import org.example.gestorapi.service.CursoService;
import org.example.gestorapi.service.PuntoInteresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/acex")
public class PuntoInteresController {

    @Autowired
    private PuntoInteresService puntoInteresService;

    @GetMapping("/puntosinteres")
    public ResponseEntity<?> getPuntoInteres() {
        List<PuntoInteres> puntoInteres = puntoInteresService.findAll();
        if (puntoInteres.isEmpty()) {
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(puntoInteres);
        }
    }

    @GetMapping("/puntosinteres/{id}")
    public ResponseEntity<?> getPuntoInteresById(@PathVariable int id) {
        PuntoInteres puntoInteres = puntoInteresService.findById(id);
        if(puntoInteres == null) {
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(puntoInteres);
        }
    }


    @PostMapping("/puntosinteres")
    public ResponseEntity<?> createPuntoInteres(@RequestBody PuntoInteres puntoInteres) {
        PuntoInteres guardado = puntoInteresService.guardar(puntoInteres);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }

    @PutMapping("/puntosinteres/{id}")
    public ResponseEntity<?> updatePuntoInteres(@PathVariable int id, @RequestBody PuntoInteres puntoInteres) {
        PuntoInteres editar = puntoInteresService.findById(id);
        if(editar == null) {
            return ResponseEntity.notFound().build();

        }else{
            return ResponseEntity.ok(puntoInteresService.actualizar(puntoInteres, id));
        }
    }

    @DeleteMapping("/puntosinteres/{id}")
    public ResponseEntity<?> deletePuntoInteres(@PathVariable Integer id) {
        PuntoInteres eliminada = puntoInteresService.findById(id);
        if(eliminada == null) {
            return ResponseEntity.notFound().build();

        }else{
            return ResponseEntity.ok(puntoInteresService.eliminar(id));
        }

    }
}

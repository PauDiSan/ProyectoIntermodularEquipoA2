package org.example.gestorapi.controller;

import org.example.gestorapi.model.Grupo;
import org.example.gestorapi.service.GrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/acex")
public class GrupoController {

    @Autowired
    private GrupoService grupoService;

    @GetMapping("/grupos")
    public ResponseEntity<?> getAllGrupos(){
        List<Grupo> grupo = grupoService.findAll();
        if(grupo.isEmpty())
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok(grupo);
    }
    @GetMapping("/grupos/{id}")
    public ResponseEntity<?> getGrupoById(@PathVariable int id){
        Grupo grupo = grupoService.findById(id);
        if(grupo == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(grupo);
    }
    @PostMapping("/grupos")
    public ResponseEntity<Grupo> nuevoGrupo(@RequestBody Grupo grupo){
        Grupo guardado= grupoService.guardar(grupo);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }
    @PutMapping("/grupos/{id}")
    public ResponseEntity<?> updateGrupo(@PathVariable int id, @RequestBody Grupo grupo){
        Grupo g= grupoService.findById(id);
        if(grupo == null)
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok(grupoService.actualizar(g,id));

    }
    @DeleteMapping("/grupos/{id}")
    public ResponseEntity<?> deleteGrupo(@PathVariable int id){
        Grupo eliminado= grupoService.eliminar(id);
        if(eliminado == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(eliminado);
    }



}

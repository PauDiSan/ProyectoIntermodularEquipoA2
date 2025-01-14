package org.example.gestorapi.controller;

import org.example.gestorapi.model.Foto;
import org.example.gestorapi.service.FotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/acex")
public class FotoController {

    @Autowired
    private FotoService fotoService;

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
    @PutMapping("/grupos/{id}")
    public ResponseEntity<?> updateFoto(@PathVariable int id, @RequestBody Foto foto){
        Foto f= fotoService.findById(id);
        if(foto == null)
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok(fotoService.actualizar(f,id));

    }
    @DeleteMapping("/grupos/{id}")
    public ResponseEntity<?> deleteFoto(@PathVariable int id){
        Foto eliminado= fotoService.eliminar(id);
        if(eliminado == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(eliminado);
    }
}

package org.example.gestorapi.controller;

import org.example.gestorapi.model.Curso;
import org.example.gestorapi.service.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/acex")
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @GetMapping("/cursos")
    public ResponseEntity<?> getCursos() {
        List<Curso> curso = cursoService.findAll();
        if (curso.isEmpty()) {
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(curso);
        }
    }

    @GetMapping("/cursos/{id}")
    public ResponseEntity<?> getCursoById(@PathVariable int id) {
        Curso curso = cursoService.findById(id);
        if(curso == null) {
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(curso);
        }
    }


    @PostMapping("/cursos")
    public ResponseEntity<?> createCurso(@RequestBody Curso curso) {
        Curso guardado = cursoService.guardar(curso);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }

    @PutMapping("/cursos/{id}")
    public ResponseEntity<?> updateCurso(@PathVariable int id, @RequestBody Curso editar) {
        Curso curso = cursoService.findById(id);
        if(curso == null) {
            return ResponseEntity.notFound().build();

        }else{
            return ResponseEntity.ok(cursoService.actualizar(editar, id));
        }
    }

    @DeleteMapping("/cursos/{id}")
    public ResponseEntity<?> deleteCurso(@PathVariable Integer id) {
        Curso eliminada = cursoService.findById(id);
        if(eliminada == null) {
            return ResponseEntity.notFound().build();

        }else{
            return ResponseEntity.ok(cursoService.eliminar(id));
        }

    }

}

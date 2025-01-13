package org.example.gestorapi.controller;

import org.example.gestorapi.model.Departamento;
import org.example.gestorapi.service.DepartamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/acex")
public class DepartamentoController {

    @Autowired
    private DepartamentoService departamentoService;

    @GetMapping("/departamentos")
    public ResponseEntity<?> getDepartamentos() {
        List<Departamento> departamento = departamentoService.findAll();
        if (departamento.isEmpty()) {
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(departamento);
        }
    }

    @GetMapping("/departamentos/{id}")
    public ResponseEntity<?> getDepartamentoById(@PathVariable int id) {
        Departamento departamento = departamentoService.findById(id);
        if(departamento == null) {
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(departamento);
        }
    }


    @PostMapping("/departamentos")
    public ResponseEntity<?> createDepartamento(@RequestBody Departamento departamento) {
        Departamento guardado = departamentoService.guardar(departamento);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }

    @PutMapping("/departamentos/{id}")
    public ResponseEntity<?> updateDepartamento(@PathVariable int id, @RequestBody Departamento editar) {
        Departamento departamento = departamentoService.findById(id);
        if(departamento == null) {
            return ResponseEntity.notFound().build();

        }else{
            return ResponseEntity.ok(departamentoService.actualizar(editar, id));
        }
    }

    @DeleteMapping("/departamentos/{id}")
    public ResponseEntity<?> deleteDepartamento(@PathVariable Integer id) {
        Departamento eliminada = departamentoService.findById(id);
        if(eliminada == null) {
            return ResponseEntity.notFound().build();

        }else{
            return ResponseEntity.ok(departamentoService.eliminar(id));
        }

    }

}

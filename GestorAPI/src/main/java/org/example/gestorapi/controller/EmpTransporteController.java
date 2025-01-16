package org.example.gestorapi.controller;


import org.example.gestorapi.model.EmpTransporte;
import org.example.gestorapi.model.Grupo;
import org.example.gestorapi.service.EmpTransporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/acex")
public class EmpTransporteController {

    @Autowired
    private EmpTransporteService empTransporteService;

    @GetMapping("/transportes")
    public ResponseEntity<?> getAllEmpTransportes() {
        List<EmpTransporte> transportes = empTransporteService.findAll();
        if(transportes.isEmpty())
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok(transportes);
    }
    @GetMapping("/transportes/{id}")
    public ResponseEntity<?> getEmpTransporteById(@PathVariable int id){
        EmpTransporte empresa = empTransporteService.findById(id);
        if(empresa == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(empresa);


    }
    @PostMapping("/transportes")
    public ResponseEntity<EmpTransporte> nuevoEmpTransporte(@RequestBody EmpTransporte empresa){
        EmpTransporte guardado= empTransporteService.guardar(empresa);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }
    @PutMapping("/transportes/{id}")
    public ResponseEntity<?> updateEmpTransporte(@PathVariable int id, @RequestBody EmpTransporte empresa){
        EmpTransporte et= empTransporteService.findById(id);
        if(et == null)
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok(empTransporteService.actualizar(empresa,id));

    }
    @DeleteMapping("/transportes/{id}")
    public ResponseEntity<?> deleteEmpTrosnporte(@PathVariable int id){
        EmpTransporte eliminado= empTransporteService.eliminar(id);
        if(eliminado == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(eliminado);
    }
}

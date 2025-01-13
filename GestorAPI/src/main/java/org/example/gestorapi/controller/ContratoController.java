package org.example.gestorapi.controller;

import org.example.gestorapi.model.Contrato;
import org.example.gestorapi.service.ContratoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/acex")
public class ContratoController {

    @Autowired
    private ContratoService contratoService;

    @GetMapping("/contratos")
    public ResponseEntity<?> getContratoes() {
        List<Contrato> contratoes = contratoService.findAll();
        if (contratoes.isEmpty()) {
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(contratoes);
        }
    }

    @GetMapping("/contratos/{id}")
    public ResponseEntity<?> getContratoById(@PathVariable int id) {
        Contrato contrato = contratoService.findById(id);
        if(contrato == null) {
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(contrato);
        }
    }


    @PostMapping("/contratos")
    public ResponseEntity<?> createContrato(@RequestBody Contrato contrato) {
        Contrato guardado = contratoService.guardar(contrato);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }

    @PutMapping("/contratos/{id}")
    public ResponseEntity<?> updateContrato(@PathVariable int id, @RequestBody Contrato editar) {
        Contrato contrato = contratoService.findById(id);
        if(contrato == null) {
            return ResponseEntity.notFound().build();

        }else{
            return ResponseEntity.ok(contratoService.actualizar(editar, id));
        }
    }

    @DeleteMapping("/contratos/{id}")
    public ResponseEntity<?> deleteContrato(@PathVariable Integer id) {
        Contrato eliminada = contratoService.findById(id);
        if(eliminada == null) {
            return ResponseEntity.notFound().build();

        }else{
            return ResponseEntity.ok(contratoService.eliminar(id));
        }

    }

}

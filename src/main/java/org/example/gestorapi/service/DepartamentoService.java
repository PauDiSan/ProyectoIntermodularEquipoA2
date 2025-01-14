package org.example.gestorapi.service;

import org.example.gestorapi.model.Departamento;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DepartamentoService {
    List<Departamento> findAll();
    Departamento findById(Integer id);
    Departamento guardar(Departamento departamento);
    Departamento actualizar(Departamento nuevo, Integer id);
    Departamento eliminar(Integer id);

}

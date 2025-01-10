package org.example.gestorapi.service;

import org.example.gestorapi.model.Grupo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GrupoService {
    List<Grupo> findAll();
    Grupo findById(Integer id);
    Grupo guardar(Grupo grupo);
    Grupo actualizar(Grupo nuevo, Integer id);
    Grupo eliminar(Integer id);
}

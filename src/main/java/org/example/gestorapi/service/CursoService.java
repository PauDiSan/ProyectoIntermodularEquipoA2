package org.example.gestorapi.service;

import org.example.gestorapi.model.Curso;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CursoService {
    List<Curso> findAll();
    Curso findById(Integer id);
    Curso guardar(Curso curso);
    Curso actualizar(Curso nuevo, Integer id);
    Curso eliminar(Integer id);

}

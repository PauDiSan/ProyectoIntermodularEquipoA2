package org.example.gestorapi.service.impl;

import org.example.gestorapi.model.Curso;
import org.example.gestorapi.repository.CursoRepository;
import org.example.gestorapi.service.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CursoServiceImpl implements CursoService {
    @Autowired
    private CursoRepository cursoRepository;
    @Override
    public List<Curso> findAll() {
        return cursoRepository.findAll();
    }

    @Override
    public Curso findById(Integer id) {
        return cursoRepository.findById(id).orElse(null);
    }

    @Override
    public Curso guardar(Curso curso) {
        return cursoRepository.save(curso);
    }

    @Override
    public Curso actualizar(Curso nuevo, Integer id) {
        if(cursoRepository.existsById(id)) {
            nuevo.setId(id);
            return cursoRepository.save(nuevo);
        }else{
            return null;
        }
    }

    @Override
    public Curso eliminar(Integer id) {
        if(cursoRepository.existsById(id)) {
            Curso curso = cursoRepository.findById(id).get();
            cursoRepository.delete(curso);
            return curso;
        }else{
            return null;
        }
    }
}

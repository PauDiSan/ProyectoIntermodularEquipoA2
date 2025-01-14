package org.example.gestorapi.service.impl;

import org.example.gestorapi.model.Grupo;
import org.example.gestorapi.repository.GrupoRepository;
import org.example.gestorapi.service.GrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GrupoServiceImpl implements GrupoService {
    @Autowired
    private GrupoRepository grupoRepository;

    @Override
    public List<Grupo> findAll() {
        return grupoRepository.findAll();
    }

    @Override
    public Grupo findById(Integer id) {
        return grupoRepository.findById(id).orElse(null);
    }

    @Override
    public Grupo guardar(Grupo grupo) {
        return grupoRepository.save(grupo);
    }

    @Override
    public Grupo actualizar(Grupo nuevo, Integer id) {
        if(grupoRepository.existsById(id)) {
            nuevo.setId(id);
            return grupoRepository.save(nuevo);
        }else{
            return null;
        }
    }

    @Override
    public Grupo eliminar(Integer id) {
        if(grupoRepository.existsById(id)) {
            Grupo nuevo = grupoRepository.findById(id).get();
            grupoRepository.delete(nuevo);
            return nuevo;
        }else{
            return null;
        }
    }
}

package org.example.gestorapi.service.impl;

import org.example.gestorapi.model.Profesor;
import org.example.gestorapi.repository.ProfesorRepository;
import org.example.gestorapi.service.ProfesorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfesorServiceImpl implements ProfesorService {
    @Autowired
    private ProfesorRepository profesorRepository;

    @Override
    public List<Profesor> findAll() {
        return profesorRepository.findAll();
    }

    @Override
    public Profesor findByUuid(String id) {
        return profesorRepository.findById(id).orElse(null);
    }

    @Override
    public Profesor guardar(Profesor profesor) {
        return profesorRepository.save(profesor);
    }

    @Override
    public Profesor actualizar(Profesor nuevo, String id) {
        if(profesorRepository.existsById(id)) {
            nuevo.setUuid(id);
            return profesorRepository.save(nuevo);
        }else{
            return null;
        }
    }

    @Override
    public Profesor eliminar(String id) {
        if(profesorRepository.existsById(id)) {
            Profesor profesor = profesorRepository.findById(id).get();
            profesorRepository.delete(profesor);
            return profesor;
        }else{
            return null;
        }
    }
}

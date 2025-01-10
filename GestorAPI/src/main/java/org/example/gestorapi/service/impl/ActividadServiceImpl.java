package org.example.gestorapi.service.impl;

import org.example.gestorapi.model.Actividad;
import org.example.gestorapi.repository.ActividadRepository;
import org.example.gestorapi.service.ActividadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActividadServiceImpl implements ActividadService {
    @Autowired
    private ActividadRepository actividadRepository;
    @Override
    public List<Actividad> findAll() {
        return actividadRepository.findAll();
    }

    @Override
    public Actividad findById(Integer id) {
        return actividadRepository.findById(id).orElse(null);
    }

    @Override
    public Actividad guardar(Actividad actividad) {
        return actividadRepository.save(actividad);
    }

    @Override
    public Actividad actualizar(Actividad nuevo, Integer id) {
        if(actividadRepository.existsById(id)) {
            nuevo.setId(id);
            return actividadRepository.save(nuevo);
        }else{
            return null;
        }
    }

    @Override
    public Actividad eliminar(Integer id) {
        if(actividadRepository.existsById(id)) {
            Actividad actividad = actividadRepository.findById(id).get();
            actividadRepository.delete(actividad);
            return actividad;
        }else{
            return null;
        }
    }
}

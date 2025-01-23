package org.example.gestorapi.service.impl;

import org.example.gestorapi.model.PuntoInteres;
import org.example.gestorapi.repository.PuntoInteresRepository;
import org.example.gestorapi.service.PuntoInteresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PuntoInteresServiceImpl implements PuntoInteresService {

    @Autowired
    private PuntoInteresRepository puntoInteresRepository;
    @Override
    public List<PuntoInteres> findAll() {
        return puntoInteresRepository.findAll();
    }

    @Override
    public PuntoInteres findById(Integer id) {
        return puntoInteresRepository.findById(id).orElse(null);
    }

    @Override
    public PuntoInteres guardar(PuntoInteres puntoInteres) {
        return puntoInteresRepository.save(puntoInteres);
    }

    @Override
    public PuntoInteres actualizar(PuntoInteres nuevo, Integer id) {
        if(puntoInteresRepository.existsById(id)) {
            nuevo.setId(id);
            return puntoInteresRepository.save(nuevo);
        }else{
            return null;
        }
    }

    @Override
    public PuntoInteres eliminar(Integer id) {
        if(puntoInteresRepository.existsById(id)) {
            PuntoInteres puntoInteres = puntoInteresRepository.findById(id).get();
            puntoInteresRepository.delete(puntoInteres);
            return puntoInteres;
        }else{
            return null;
        }
    }
}

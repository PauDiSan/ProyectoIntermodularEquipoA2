package org.example.gestorapi.service;

import org.example.gestorapi.model.PuntoInteres;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PuntoInteresService {
    List<PuntoInteres> findAll();
    PuntoInteres findById(Integer id);
    PuntoInteres guardar(PuntoInteres puntoInteres);
    PuntoInteres actualizar(PuntoInteres nuevo, Integer id);
    PuntoInteres eliminar(Integer id);
}

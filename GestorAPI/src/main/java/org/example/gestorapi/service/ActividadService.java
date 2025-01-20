package org.example.gestorapi.service;

import org.example.gestorapi.model.Actividad;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ActividadService {
    List<Actividad> findAll();
    Actividad findById(Integer id);
    Actividad guardar(Actividad actividad);
    Actividad actualizar(Actividad nuevo, Integer id);
    Actividad eliminar(Integer id);
    void excel(Actividad actividad);



}

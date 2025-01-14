package org.example.gestorapi.service;

import org.example.gestorapi.model.Foto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FotoService {
    List<Foto> findAll();
    Foto findById(Integer id);
    Foto guardar(Foto foto);
    Foto actualizar(Foto nuevo, Integer id);
    Foto eliminar(Integer id);
}

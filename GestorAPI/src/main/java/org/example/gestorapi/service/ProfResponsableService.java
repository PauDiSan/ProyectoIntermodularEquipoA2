package org.example.gestorapi.service;

import org.example.gestorapi.model.ProfResponsable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProfResponsableService {

    List<ProfResponsable> findAll();
    ProfResponsable findById(Integer id);
    ProfResponsable guardar(ProfResponsable profResponsable);
    ProfResponsable actualizar(ProfResponsable nuevo, Integer id);
    ProfResponsable eliminar(Integer id);
}

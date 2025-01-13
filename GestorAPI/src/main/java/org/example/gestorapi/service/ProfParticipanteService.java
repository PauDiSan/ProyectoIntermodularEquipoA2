package org.example.gestorapi.service;

import org.example.gestorapi.model.ProfParticipante;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProfParticipanteService {
    List<ProfParticipante> findAll();
    ProfParticipante findById(Integer id);
    ProfParticipante guardar(ProfParticipante profParticipante);
    ProfParticipante actualizar(ProfParticipante nuevo, Integer id);
    ProfParticipante eliminar(Integer id);
}

package org.example.gestorapi.service;

import org.example.gestorapi.model.GrupoParticipante;
import org.example.gestorapi.model.ProfParticipante;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GrupoParticipanteService {
    List<GrupoParticipante> findAll();
    GrupoParticipante findById(Integer id);
    GrupoParticipante guardar(GrupoParticipante grupoParticipante);
    GrupoParticipante actualizar(GrupoParticipante nuevo, Integer id);
    GrupoParticipante eliminar(Integer id);
    List<GrupoParticipante> findGrupoParticipantesByActividades_Id(Integer actividadId);
}

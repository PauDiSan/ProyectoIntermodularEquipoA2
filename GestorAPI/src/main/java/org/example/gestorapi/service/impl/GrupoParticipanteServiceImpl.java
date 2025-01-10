package org.example.gestorapi.service.impl;

import org.example.gestorapi.model.GrupoParticipante;
import org.example.gestorapi.repository.GrupoParticipanteRepository;
import org.example.gestorapi.service.GrupoParticipanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GrupoParticipanteServiceImpl implements GrupoParticipanteService {
    @Autowired
    private GrupoParticipanteRepository grupoParticipanteRepository;

    @Override
    public List<GrupoParticipante> findAll() {
        return grupoParticipanteRepository.findAll();
    }

    @Override
    public GrupoParticipante findById(Integer id) {
        return grupoParticipanteRepository.findById(id).orElse(null);
    }

    @Override
    public GrupoParticipante guardar(GrupoParticipante grupoParticipante) {
        return grupoParticipanteRepository.save(grupoParticipante);
    }

    @Override
    public GrupoParticipante actualizar(GrupoParticipante nuevo, Integer id) {
        if(grupoParticipanteRepository.existsById(id)) {
            nuevo.setId(id);
            return grupoParticipanteRepository.save(nuevo);
        }else{
            return null;
        }
    }

    @Override
    public GrupoParticipante eliminar(Integer id) {
        if(grupoParticipanteRepository.existsById(id)) {
            GrupoParticipante grupoParticipante = grupoParticipanteRepository.findById(id).get();
            grupoParticipanteRepository.delete(grupoParticipante);
            return grupoParticipante;
        }else{
            return null;
        }
    }
}

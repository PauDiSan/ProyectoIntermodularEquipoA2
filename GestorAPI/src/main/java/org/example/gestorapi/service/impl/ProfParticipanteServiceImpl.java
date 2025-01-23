package org.example.gestorapi.service.impl;

import org.example.gestorapi.model.ProfParticipante;
import org.example.gestorapi.repository.ProfParticipanteRepository;
import org.example.gestorapi.service.ProfParticipanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfParticipanteServiceImpl implements ProfParticipanteService {
    @Autowired
    private ProfParticipanteRepository profParticipanteRepository;

    @Override
    public List<ProfParticipante> findAll() {
        return profParticipanteRepository.findAll();
    }

    @Override
    public ProfParticipante findById(Integer id) {
        return profParticipanteRepository.findById(id).orElse(null);
    }

    @Override
    public ProfParticipante guardar(ProfParticipante profParticipante) {
        return profParticipanteRepository.save(profParticipante);
    }

    @Override
    public ProfParticipante actualizar(ProfParticipante nuevo, Integer id) {
        if(profParticipanteRepository.existsById(id)) {
            nuevo.setId(id);
            return profParticipanteRepository.save(nuevo);
        }else {
            return null;
        }
    }

    @Override
    public ProfParticipante eliminar(Integer id) {
        if(profParticipanteRepository.existsById(id)) {
            ProfParticipante profParticipante = profParticipanteRepository.findById(id).get();
            profParticipanteRepository.delete(profParticipante);
            return profParticipante;

        }else {
            return null;
        }
    }

    @Override
    public  List<ProfParticipante> findProfParticipanteByActividadId(Integer actividadId){
        return profParticipanteRepository.findProfParticipanteByActividadId(actividadId);
    }

}

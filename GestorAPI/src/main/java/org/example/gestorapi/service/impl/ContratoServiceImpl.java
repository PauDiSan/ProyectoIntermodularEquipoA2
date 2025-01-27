package org.example.gestorapi.service.impl;

import org.example.gestorapi.model.Actividad;
import org.example.gestorapi.model.Contrato;
import org.example.gestorapi.repository.ActividadRepository;
import org.example.gestorapi.repository.ContratoRepository;
import org.example.gestorapi.service.ActividadService;
import org.example.gestorapi.service.ContratoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContratoServiceImpl implements ContratoService {
    @Autowired
    private ContratoRepository contratoRepository;
    @Override
    public List<Contrato> findAll() {
        return contratoRepository.findAll();
    }

    @Override
    public Contrato findById(Integer id) {
        return contratoRepository.findById(id).orElse(null);
    }

    @Override
    public Contrato guardar(Contrato contrato) {
        return contratoRepository.save(contrato);
    }

    @Override
    public Contrato actualizar(Contrato nuevo, Integer id) {
        if(contratoRepository.existsById(id)) {
            nuevo.setId(id);
            return contratoRepository.save(nuevo);
        }else{
            return null;
        }
    }

    @Override
    public Contrato eliminar(Integer id) {
        if(contratoRepository.existsById(id)) {
            Contrato contrato = contratoRepository.findById(id).get();
            contratoRepository.delete(contrato);
            return contrato;
        }else{
            return null;
        }
    }

    @Override
    public List<Contrato> contratosByActividadId(Integer id){
        return contratoRepository.findContratoByActividadId(id);

    }


}

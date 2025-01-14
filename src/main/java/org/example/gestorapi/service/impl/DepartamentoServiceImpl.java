package org.example.gestorapi.service.impl;

import org.example.gestorapi.model.Departamento;
import org.example.gestorapi.repository.DepartamentoRepository;
import org.example.gestorapi.service.DepartamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartamentoServiceImpl implements DepartamentoService {
    @Autowired
    private DepartamentoRepository departamentoRepository;
    @Override
    public List<Departamento> findAll() {
        return departamentoRepository.findAll();
    }

    @Override
    public Departamento findById(Integer id) {
        return departamentoRepository.findById(id).orElse(null);
    }

    @Override
    public Departamento guardar(Departamento departamento) {
        return departamentoRepository.save(departamento);
    }

    @Override
    public Departamento actualizar(Departamento nuevo, Integer id) {
        if(departamentoRepository.existsById(id)) {
            nuevo.setId(id);
            return departamentoRepository.save(nuevo);
        }else{
            return null;
        }
    }

    @Override
    public Departamento eliminar(Integer id) {
        if(departamentoRepository.existsById(id)) {
            Departamento departamento = departamentoRepository.findById(id).get();
            departamentoRepository.delete(departamento);
            return departamento;
        }else{
            return null;
        }
    }
}

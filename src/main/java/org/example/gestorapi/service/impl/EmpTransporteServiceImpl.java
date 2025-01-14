package org.example.gestorapi.service.impl;

import org.example.gestorapi.model.EmpTransporte;
import org.example.gestorapi.repository.EmpTransporteRepository;
import org.example.gestorapi.service.EmpTransporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpTransporteServiceImpl implements EmpTransporteService {
    @Autowired
    private EmpTransporteRepository empTransporteRepository;
    @Override
    public List<EmpTransporte> findAll() {
        return empTransporteRepository.findAll();
    }

    @Override
    public EmpTransporte findById(Integer id) {
        return empTransporteRepository.findById(id).orElse(null);
    }

    @Override
    public EmpTransporte guardar(EmpTransporte empTransporte) {
        return empTransporteRepository.save(empTransporte);
    }

    @Override
    public EmpTransporte actualizar(EmpTransporte nuevo, Integer id) {
        if(empTransporteRepository.existsById(id)) {
            nuevo.setId(id);
            return empTransporteRepository.save(nuevo);
        }else{
            return null;
        }
    }

    @Override
    public EmpTransporte eliminar(Integer id) {
        if(empTransporteRepository.existsById(id)) {
            EmpTransporte empTransporte = empTransporteRepository.findById(id).get();
            empTransporteRepository.delete(empTransporte);
            return empTransporte;
        }else{
            return null;
        }
    }
}

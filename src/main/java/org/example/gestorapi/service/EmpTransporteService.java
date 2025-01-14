package org.example.gestorapi.service;

import org.example.gestorapi.model.EmpTransporte;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EmpTransporteService {
    List<EmpTransporte> findAll();
    EmpTransporte findById(Integer id);
    EmpTransporte guardar(EmpTransporte empTransporte);
    EmpTransporte actualizar(EmpTransporte nuevo, Integer id);
    EmpTransporte eliminar(Integer id);






}

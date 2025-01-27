package org.example.gestorapi.service;

import org.example.gestorapi.model.Contrato;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ContratoService {
    List<Contrato> findAll();
    Contrato findById(Integer id);
    Contrato guardar(Contrato contrato);
    Contrato actualizar(Contrato nuevo, Integer id);
    Contrato eliminar(Integer id);
    List<Contrato> contratosByActividadId(Integer id);

}

package org.example.gestorapi.service.impl;

import org.example.gestorapi.model.ProfResponsable;
import org.example.gestorapi.repository.ProfResponsableRepository;
import org.example.gestorapi.service.ProfResponsableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfResponsableServiceImpl implements ProfResponsableService {
    @Autowired
    private ProfResponsableRepository profResponsableRepository;

    @Override
    public List<ProfResponsable> findAll() {
        return profResponsableRepository.findAll();
    }

    @Override
    public ProfResponsable findById(Integer id) {
        return profResponsableRepository.findById(id).orElse(null);
    }

    @Override
    public ProfResponsable guardar(ProfResponsable profResponsable) {
        return profResponsableRepository.save(profResponsable);
    }

    @Override
    public ProfResponsable actualizar(ProfResponsable nuevo, Integer id) {
        if(profResponsableRepository.existsById(id)) {
            nuevo.setId(id);
            return profResponsableRepository.save(nuevo);
        }else{
            return null;
        }
    }

    @Override
    public ProfResponsable eliminar(Integer id) {
        if(profResponsableRepository.existsById(id)) {
            ProfResponsable profResponsable = profResponsableRepository.findById(id).get();
            profResponsableRepository.delete(profResponsable);
            return profResponsable;
        }else {
            return null;
        }
    }
}

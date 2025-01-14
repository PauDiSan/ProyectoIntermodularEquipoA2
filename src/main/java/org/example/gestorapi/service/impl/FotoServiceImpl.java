package org.example.gestorapi.service.impl;

import org.example.gestorapi.model.Foto;
import org.example.gestorapi.repository.FotoRepository;
import org.example.gestorapi.service.FotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FotoServiceImpl implements FotoService {

    @Autowired
    private FotoRepository fotoRepository;

    @Override
    public List<Foto> findAll() {
        return fotoRepository.findAll();
    }

    @Override
    public Foto findById(Integer id) {
        return fotoRepository.findById(id).orElse(null);
    }

    @Override
    public Foto guardar(Foto foto) {
        return fotoRepository.save(foto);
    }

    @Override
    public Foto actualizar(Foto nuevo, Integer id) {
        if(fotoRepository.existsById(id)) {
            nuevo.setId(id);
            return fotoRepository.save(nuevo);
        }else {
            return null;
        }
    }

    @Override
    public Foto eliminar(Integer id) {
        if(fotoRepository.existsById(id)) {
            Foto foto = fotoRepository.findById(id).get();
            fotoRepository.delete(foto);
            return foto;
        }else{
            return null;
        }
    }
}

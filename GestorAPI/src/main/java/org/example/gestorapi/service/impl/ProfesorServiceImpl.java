package org.example.gestorapi.service.impl;

import org.example.gestorapi.model.Profesor;
import org.example.gestorapi.repository.ProfesorRepository;
import org.example.gestorapi.service.ProfesorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
public class ProfesorServiceImpl implements ProfesorService {
    @Autowired
    private ProfesorRepository profesorRepository;

    @Override
    public List<Profesor> findAll() {
        return profesorRepository.findAll();
    }

    @Override
    public Profesor findByEmail(String correo) {
        return profesorRepository.findByCorreo(correo);
    }

    @Override
    public Profesor guardar(Profesor profesor) {
        return profesorRepository.save(profesor);
    }

    @Override
    public Profesor actualizar(Profesor nuevo, String id) {
        if(profesorRepository.existsById(id)) {
            nuevo.setUuid(id);
            return profesorRepository.save(nuevo);
        }else{
            return null;
        }
    }

    @Override
    public Profesor eliminar(String id) {
        if(profesorRepository.existsById(id)) {
            Profesor profesor = profesorRepository.findById(id).get();
            profesorRepository.delete(profesor);
            return profesor;
        }else{
            return null;
        }
    }

    @Override
    public Profesor inicioSesion(String email, String password) {
        String md5Password = "";
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(password.getBytes(),0, password.length());
            md5Password = new BigInteger(1,messageDigest.digest()).toString(16);
            if (md5Password.length() < 32) {
                md5Password = "0" + md5Password;
            }
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return profesorRepository.findByCorreoAndPassword(email, md5Password);
    }

    @Override
    public Profesor findById(String id) {
        return profesorRepository.findById(id).orElse(null);
    }

}

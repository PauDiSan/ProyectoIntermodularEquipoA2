package org.example.gestorapi.service;

import org.example.gestorapi.model.ProfParticipante;
import org.example.gestorapi.model.Profesor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProfesorService {
    List<Profesor> findAll();
    Profesor findByEmail(String correo);
    Profesor guardar(Profesor profesor);
    Profesor actualizar(Profesor nuevo, String id);
    Profesor eliminar(String id);
    Profesor inicioSesion(String email, String password);
    Profesor findById(String id);
}

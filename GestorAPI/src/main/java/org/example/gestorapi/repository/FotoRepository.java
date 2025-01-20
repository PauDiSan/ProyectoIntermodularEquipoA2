package org.example.gestorapi.repository;

import org.example.gestorapi.model.Actividad;
import org.example.gestorapi.model.Foto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FotoRepository extends JpaRepository<Foto, Integer> {
    List<Foto> findByActividad(Actividad actividad);
}

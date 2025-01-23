package org.example.gestorapi.repository;

import org.example.gestorapi.model.Actividad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ActividadRepository extends JpaRepository<Actividad, Integer> {



}

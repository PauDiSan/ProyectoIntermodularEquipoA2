package org.example.gestorapi.repository;

import org.example.gestorapi.model.Actividad;
import org.example.gestorapi.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Integer> {


}

package org.example.gestorapi.repository;

import jakarta.validation.constraints.Size;
import org.example.gestorapi.model.Profesor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfesorRepository extends JpaRepository<Profesor, String> {
}

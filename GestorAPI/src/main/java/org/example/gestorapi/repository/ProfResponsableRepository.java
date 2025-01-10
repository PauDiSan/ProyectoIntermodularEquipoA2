package org.example.gestorapi.repository;

import org.example.gestorapi.model.ProfResponsable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfResponsableRepository extends JpaRepository<ProfResponsable, Integer> {

}

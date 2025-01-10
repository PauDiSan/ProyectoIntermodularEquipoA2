package org.example.gestorapi.repository;

import org.example.gestorapi.model.GrupoParticipante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrupoParticipanteRepository extends JpaRepository<GrupoParticipante, Integer> {
}

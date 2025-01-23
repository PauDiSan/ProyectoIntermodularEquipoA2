package org.example.gestorapi.repository;

import org.example.gestorapi.model.GrupoParticipante;
import org.example.gestorapi.model.ProfParticipante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GrupoParticipanteRepository extends JpaRepository<GrupoParticipante, Integer> {
    List<GrupoParticipante> findGrupoParticipantesByActividades_Id(Integer actividadesId);
}

package org.example.gestorapi.repository;

import org.example.gestorapi.model.Actividad;
import org.example.gestorapi.model.EmpTransporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpTransporteRepository extends JpaRepository<EmpTransporte, Integer> {


}

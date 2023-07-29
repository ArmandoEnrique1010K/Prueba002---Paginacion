package com.prueba02.repository;

import com.prueba02.entity.PersonaEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PersonaRepository extends JpaRepository<PersonaEntity, Long>{
    
    // CONSULTA PARA FILTRAR RESULTADOS QUE TENGAN UN ESTADO TRUE
    @Query(value = "select x from PersonaEntity x where x.estado = true")
    List<PersonaEntity> listarPorEstadoTrue(Boolean estado);
    
}

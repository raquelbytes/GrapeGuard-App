package com.raquelbytes.grapeguardapi.Repository;

import com.raquelbytes.grapeguardapi.Model.VinedoTratamiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VinedoTratamientoRepository extends JpaRepository<VinedoTratamiento, Integer> {
    List<VinedoTratamiento> findByVinedoId(Integer vinedoId);

    boolean existsByTratamientoId(Integer tratamientoId);


    void deleteByTratamientoId(Integer tratamientoId);

}

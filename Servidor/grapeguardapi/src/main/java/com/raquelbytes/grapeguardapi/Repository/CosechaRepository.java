package com.raquelbytes.grapeguardapi.Repository;

import com.raquelbytes.grapeguardapi.Model.Cosecha;
import com.raquelbytes.grapeguardapi.Model.Vinedo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CosechaRepository extends JpaRepository<Cosecha, Integer> {
    List<Cosecha> findByVinedoId(Integer vinedoId);

    List<Cosecha> findByVinedoIdAndFechaCosecha(Vinedo vinedoId, LocalDate fechaCosecha);



}

package com.raquelbytes.grapeguardapi.Repository;

import com.raquelbytes.grapeguardapi.Model.Tratamiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TratamientoRepository extends JpaRepository<Tratamiento, Integer> {
    @Query("SELECT t FROM Tratamiento t WHERE t.usuario.id = :idUsuario")
    List<Tratamiento> findByidUsuario(@Param("idUsuario") Integer idUsuario);
}

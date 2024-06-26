package com.raquelbytes.grapeguardapi.Repository;

import com.raquelbytes.grapeguardapi.Model.Usuario;
import com.raquelbytes.grapeguardapi.Model.Vinedo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface VinedoRepository extends JpaRepository<Vinedo, Integer> {

    List<Vinedo> findByUsuario(Usuario usuario);
    boolean existsByNombreAndUbicacion(String nombre, String ubicacion);

    @Query("SELECT SUM(v.hectareas) FROM Vinedo v WHERE v.usuario = :usuario")
    BigDecimal sumHectareasByUsuarioId(@Param("usuario") Usuario usuario);


}

package com.raquelbytes.grapeguardapi.Repository;

import com.raquelbytes.grapeguardapi.Model.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TareaRepository extends JpaRepository<Tarea, Integer> {

    // Obtener tareas por viñedo
    List<Tarea> findByVinedoId(Integer vinedoId);

    // Consultar tareas por viñedo con estado pendiente y en progreso
    List<Tarea> findByVinedoIdAndEstadoIn(Integer vinedoId, String... estados);

    // Consultar tareas por viñedo con estado específico
    List<Tarea> findByVinedoIdAndEstado(Integer vinedoId, Tarea.EstadoTarea estado);
}

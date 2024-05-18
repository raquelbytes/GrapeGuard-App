package com.raquelbytes.grapeguardapi.Controller;

import com.raquelbytes.grapeguardapi.Model.Tarea;
import com.raquelbytes.grapeguardapi.Model.Vinedo;
import com.raquelbytes.grapeguardapi.Repository.TareaRepository;
import com.raquelbytes.grapeguardapi.Repository.VinedoRepository;
import com.raquelbytes.grapeguardapi.ApiMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiMap.TAREA_BASE)
public class TareaController {

    @Autowired
    private TareaRepository tareaRepository;
    @Autowired
    private VinedoRepository vinedoRepository;

    // Obtener tareas por viñedo
    @GetMapping(ApiMap.TAREA_POR_VINEDO_ID)
    public ResponseEntity<List<Tarea>> obtenerTareasPorVinedo(@PathVariable Integer idVinedo) {
        List<Tarea> tareas = tareaRepository.findByVinedoId(idVinedo);
        if (!tareas.isEmpty()) {
            return ResponseEntity.ok(tareas);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Consultar tareas por viñedo con estado pendiente y en progreso
    @GetMapping(ApiMap.TAREA_PENDIENTES_POR_VINEDO_ID)
    public ResponseEntity<List<Tarea>> consultarTareasPendientesPorVinedo(@PathVariable Integer idVinedo) {
        List<Tarea> tareasPendientes = tareaRepository.findByVinedoIdAndEstadoIn(idVinedo, "Pendiente", "EnProgreso");
        if (!tareasPendientes.isEmpty()) {
            return ResponseEntity.ok(tareasPendientes);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Consultar tareas por viñedo terminadas
    @GetMapping(ApiMap.TAREA_TERMINADAS_POR_VINEDO_ID)
    public ResponseEntity<List<Tarea>> consultarTareasTerminadasPorVinedo(@PathVariable Integer idVinedo) {
        List<Tarea> tareasTerminadas = tareaRepository.findByVinedoIdAndEstado(idVinedo, Tarea.EstadoTarea.Completada);
        if (!tareasTerminadas.isEmpty()) {
            return ResponseEntity.ok(tareasTerminadas);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Añadir tarea al viñedo
    @PostMapping(ApiMap.TAREA_AGREGAR_POR_VINEDO_ID)
    public ResponseEntity<String> agregarTareaAlVinedo(@PathVariable Integer idVinedo, @RequestBody Tarea nuevaTarea) {
        Vinedo vinedo = vinedoRepository.findById(idVinedo)
                .orElseThrow(() -> new RuntimeException("Viñedo no encontrado con ID: " + idVinedo));

        nuevaTarea.setVinedo(vinedo);
        tareaRepository.save(nuevaTarea);

        return ResponseEntity.ok("Tarea añadida al viñedo correctamente");
    }

    // Borrar tarea del viñedo
    @DeleteMapping(ApiMap.TAREA_ELIMINAR_POR_ID)
    public ResponseEntity<String> borrarTareaDelVinedo(@PathVariable Integer id) {
        if (tareaRepository.existsById(id)) {
            tareaRepository.deleteById(id);
            return ResponseEntity.ok("Tarea eliminada correctamente");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

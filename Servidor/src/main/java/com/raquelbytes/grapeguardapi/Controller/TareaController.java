package com.raquelbytes.grapeguardapi.Controller;

import com.raquelbytes.grapeguardapi.Model.Tarea;
import com.raquelbytes.grapeguardapi.Repository.TareaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TareaController {

    @Autowired
    private TareaRepository tareaRepository;

    // Métodos para la entidad Tarea

    @GetMapping("/tareas")
    public List<Tarea> obtenerTareas() {
        return tareaRepository.findAll();
    }

    @GetMapping("/tareas/{id}")
    public Tarea obtenerTareaPorId(@PathVariable(value = "id") Integer tareaId) {
        return tareaRepository.findById(tareaId)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada con ID: " + tareaId));
    }

    @PostMapping("/tareas")
    public Tarea crearTarea(@RequestBody Tarea tarea) {
        return tareaRepository.save(tarea);
    }

    @PutMapping("/tareas/{id}")
    public Tarea actualizarTarea(@PathVariable(value = "id") Integer tareaId,
                                 @RequestBody Tarea tareaDetails) {
        Tarea tarea = tareaRepository.findById(tareaId)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada con ID: " + tareaId));

        tarea.setTarea(tareaDetails.getTarea());
        tarea.setEstado(tareaDetails.getEstado());
        tarea.setRecordatorio(tareaDetails.getRecordatorio());
        tarea.setFechaRealizacion(tareaDetails.getFechaRealizacion());

        return tareaRepository.save(tarea);
    }

    @DeleteMapping("/tareas/{id}")
    public void eliminarTarea(@PathVariable(value = "id") Integer tareaId) {
        Tarea tarea = tareaRepository.findById(tareaId)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada con ID: " + tareaId));

        tareaRepository.delete(tarea);
    }
}

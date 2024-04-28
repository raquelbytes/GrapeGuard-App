package com.raquelbytes.grapeguardapi.Controller;

import com.raquelbytes.grapeguardapi.Model.Nota;
import com.raquelbytes.grapeguardapi.Repository.NotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class NotaController {

    @Autowired
    private NotaRepository notaRepository;

    // Métodos para la entidad Nota

    @GetMapping("/notas")
    public List<Nota> obtenerNotas() {
        return notaRepository.findAll();
    }

    @GetMapping("/notas/{id}")
    public Nota obtenerNotaPorId(@PathVariable(value = "id") Integer notaId) {
        return notaRepository.findById(notaId)
                .orElseThrow(() -> new RuntimeException("Nota no encontrada con ID: " + notaId));
    }

    @PostMapping("/notas")
    public Nota crearNota(@RequestBody Nota nota) {
        return notaRepository.save(nota);
    }

    @PutMapping("/notas/{id}")
    public Nota actualizarNota(@PathVariable(value = "id") Integer notaId,
                               @RequestBody Nota notaDetails) {
        Nota nota = notaRepository.findById(notaId)
                .orElseThrow(() -> new RuntimeException("Nota no encontrada con ID: " + notaId));

        nota.setNota(notaDetails.getNota());
        nota.setFechaCreacion(notaDetails.getFechaCreacion());
        nota.setPrioridad(notaDetails.getPrioridad());

        return notaRepository.save(nota);
    }

    @DeleteMapping("/notas/{id}")
    public void eliminarNota(@PathVariable(value = "id") Integer notaId) {
        Nota nota = notaRepository.findById(notaId)
                .orElseThrow(() -> new RuntimeException("Nota no encontrada con ID: " + notaId));

        notaRepository.delete(nota);
    }
}


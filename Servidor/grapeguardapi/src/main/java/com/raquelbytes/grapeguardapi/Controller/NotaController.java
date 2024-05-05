package com.raquelbytes.grapeguardapi.Controller;

import com.raquelbytes.grapeguardapi.Model.Nota;
import com.raquelbytes.grapeguardapi.Model.Vinedo;
import com.raquelbytes.grapeguardapi.Repository.NotaRepository;
import com.raquelbytes.grapeguardapi.Repository.VinedoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class NotaController {

    @Autowired
    private NotaRepository notaRepository;
    @Autowired
    private VinedoRepository vinedoRepository;

    @GetMapping("/notas")
    public List<Nota> obtenerNotas() {
        return notaRepository.findAll();
    }

    @GetMapping("/notas/{id}")
    public Nota obtenerNotaPorId(@PathVariable(value = "id") Integer notaId) {
        return notaRepository.findById(notaId)
                .orElseThrow(() -> new RuntimeException("Nota no encontrada con ID: " + notaId));
    }

    @PostMapping("/vinedo/{idVinedo}/notas")
    public ResponseEntity<Nota> crearNotaEnVinedo(@PathVariable Integer idVinedo, @RequestBody Nota nota) {
        Optional<Vinedo> optionalVinedo = vinedoRepository.findById(idVinedo);

        if (optionalVinedo.isPresent()) {
            Vinedo vinedo = optionalVinedo.get();
            nota.setVinedo(vinedo);
            Nota nuevaNota = notaRepository.save(nota);
            return ResponseEntity.ok(nuevaNota);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PutMapping("/notas/{id}")
    public Nota actualizarNota(@PathVariable(value = "id") Integer notaId,
                               @RequestBody Nota notaDetails) {
        Nota nota = notaRepository.findById(notaId)
                .orElseThrow(() -> new RuntimeException("Nota no encontrada con ID: " + notaId));

        nota.setNota(notaDetails.getNota());
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


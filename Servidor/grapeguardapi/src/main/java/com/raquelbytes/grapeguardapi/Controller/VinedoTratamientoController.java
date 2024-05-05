package com.raquelbytes.grapeguardapi.Controller;

import com.raquelbytes.grapeguardapi.Model.Tratamiento;
import com.raquelbytes.grapeguardapi.Model.Vinedo;
import com.raquelbytes.grapeguardapi.Model.VinedoTratamiento;
import com.raquelbytes.grapeguardapi.Repository.VinedoRepository;
import com.raquelbytes.grapeguardapi.Repository.VinedoTratamientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/vinedo-tratamientos")
public class VinedoTratamientoController {



    @Autowired
    VinedoTratamientoRepository vinedoTratamientoRepository;
    @Autowired
    VinedoRepository vinedoRepository;


    // Obtener tratamientos por viñedo
    @GetMapping("/vinedo/{idVinedo}")
    public ResponseEntity<List<VinedoTratamiento>> obtenerTratamientosPorVinedo(@PathVariable Integer idVinedo) {
        List<VinedoTratamiento> vinedoTratamientos = vinedoTratamientoRepository.findByVinedoId(idVinedo);
        if (!vinedoTratamientos.isEmpty()) {
            return ResponseEntity.ok(vinedoTratamientos);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<VinedoTratamiento> getVinedoTratamientoById(@PathVariable("id") Integer id) {
        VinedoTratamiento vinedoTratamiento = vinedoTratamientoRepository.findById(id).orElse(null);
        if (vinedoTratamiento != null) {
            return new ResponseEntity<>(vinedoTratamiento, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

   /*   @PostMapping("/vinedo/{idVinedo}")
    public ResponseEntity<VinedoTratamiento> addVinedoTratamiento(@PathVariable Integer idVinedo, @RequestBody VinedoTratamiento vinedoTratamiento) {
        Optional<Vinedo> optionalVinedo = vinedoRepository.findById(idVinedo);
        if (optionalVinedo.isPresent()) {
            Vinedo vinedo = optionalVinedo.get();
            vinedoTratamiento.setVinedo(vinedo);
            VinedoTratamiento newVinedoTratamiento = vinedoTratamientoRepository.save(vinedoTratamiento);
            return new ResponseEntity<>(newVinedoTratamiento, HttpStatus.CREATED);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


  @PutMapping("/{id}")
    public ResponseEntity<VinedoTratamiento> updateVinedoTratamiento(@PathVariable("id") Integer id, @RequestBody VinedoTratamiento vinedoTratamiento) {
        VinedoTratamiento existingVinedoTratamiento = vinedoTratamientoRepository.findById(id).orElse(null);
        if (existingVinedoTratamiento != null) {
            vinedoTratamiento.setId(id);
            VinedoTratamiento updatedVinedoTratamiento = vinedoTratamientoRepository.save(vinedoTratamiento);
            return new ResponseEntity<>(updatedVinedoTratamiento, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    } */

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteVinedoTratamiento(@PathVariable("id") Integer id) {
        vinedoTratamientoRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

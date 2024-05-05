package com.raquelbytes.grapeguardapi.Controller;

import com.raquelbytes.grapeguardapi.Model.Cosecha;
import com.raquelbytes.grapeguardapi.Model.Usuario;
import com.raquelbytes.grapeguardapi.Model.Vinedo;
import com.raquelbytes.grapeguardapi.Repository.CosechaRepository;
import com.raquelbytes.grapeguardapi.Repository.UsuarioRepository;
import com.raquelbytes.grapeguardapi.Repository.VinedoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cosecha")
public class CosechaController {

    @Autowired
    private CosechaRepository cosechaRepository;

    @Autowired
    private VinedoRepository vinedoRepository;

    // Consultar cosechas por viñedo
    @GetMapping("/vinedo/{idVinedo}")
    public ResponseEntity<List<Cosecha>> consultarCosechasPorVinedo(@PathVariable Integer idVinedo) {
        List<Cosecha> cosechas = cosechaRepository.findByVinedoId(idVinedo);
        if (!cosechas.isEmpty()) {
            return ResponseEntity.ok(cosechas);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    // Añadir cosecha a un viñedo específico
    @PostMapping("/vinedo/{idVinedo}/cosecha/nuevo")
    public ResponseEntity<Cosecha> agregarCosecha(@PathVariable Integer idVinedo, @RequestBody Cosecha nuevaCosecha) {

        Optional<Vinedo> optionalVinedo = vinedoRepository.findById(idVinedo);
        if (optionalVinedo.isPresent()) {
            Vinedo vinedo = optionalVinedo.get();


            nuevaCosecha.setVinedo(vinedo);


            Cosecha cosechaGuardada = cosechaRepository.save(nuevaCosecha);


            return ResponseEntity.ok(cosechaGuardada);
        } else {

            return ResponseEntity.notFound().build();
        }
    }


 /*   // Modificar cosecha de un viñedo específico
    @PutMapping("/vinedo/{idVinedo}/cosecha/{idCosecha}")
    public ResponseEntity<Cosecha> modificarCosecha(@PathVariable Integer idVinedo, @PathVariable Integer idCosecha, @RequestBody Cosecha cosechaModificada) {
        Optional<Vinedo> optionalVinedo = vinedoRepository.findById(idVinedo);
        if (optionalVinedo.isPresent()) {
            Vinedo vinedo = optionalVinedo.get();
            Optional<Cosecha> optionalCosecha = cosechaRepository.findById(idCosecha);
            if (optionalCosecha.isPresent()) {
                Cosecha cosechaExistente = optionalCosecha.get();
                cosechaModificada = cosechaExistente;
                cosechaModificada.setId(idCosecha);
                cosechaModificada.setVinedo(vinedo);
                cosechaModificada = cosechaRepository.save(cosechaModificada);
                    return ResponseEntity.ok(cosechaModificada);
                } else {
                    return ResponseEntity.notFound().build();
                }
            } else {
                return ResponseEntity.notFound().build();
            }
        } */




    // Borrar cosecha
    @DeleteMapping("/{id}")
    public ResponseEntity<String> borrarCosecha(@PathVariable Integer id) {
        if (cosechaRepository.existsById(id)) {
            cosechaRepository.deleteById(id);
            return ResponseEntity.ok("Cosecha eliminada correctamente");
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}

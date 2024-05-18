package com.raquelbytes.grapeguardapi.Controller;

import com.raquelbytes.grapeguardapi.Model.Cosecha;
import com.raquelbytes.grapeguardapi.Model.Usuario;
import com.raquelbytes.grapeguardapi.Model.Vinedo;
import com.raquelbytes.grapeguardapi.Repository.CosechaRepository;
import com.raquelbytes.grapeguardapi.Repository.UsuarioRepository;
import com.raquelbytes.grapeguardapi.Repository.VinedoRepository;
import com.raquelbytes.grapeguardapi.ApiMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(ApiMap.COSECHA_BASE)
public class CosechaController {

    @Autowired
    private CosechaRepository cosechaRepository;

    @Autowired
    private VinedoRepository vinedoRepository;

    // Consultar cosechas por viñedo
    @GetMapping(ApiMap.COSECHA_POR_VINEDO_ID)
    public ResponseEntity<List<Cosecha>> consultarCosechasPorVinedo(@PathVariable Integer idVinedo) {
        List<Cosecha> cosechas = cosechaRepository.findByVinedoId(idVinedo);
        if (!cosechas.isEmpty()) {
            return ResponseEntity.ok(cosechas);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Añadir cosecha a un viñedo específico
    @PostMapping(ApiMap.COSECHA_AGREGAR_POR_VINEDO_ID)
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

    // Borrar cosecha
    @DeleteMapping(ApiMap.COSECHA_ELIMINAR_POR_ID)
    public ResponseEntity<String> borrarCosecha(@PathVariable Integer id) {
        if (cosechaRepository.existsById(id)) {
            cosechaRepository.deleteById(id);
            return ResponseEntity.ok("Cosecha eliminada correctamente");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

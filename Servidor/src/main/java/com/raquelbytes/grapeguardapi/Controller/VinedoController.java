package com.raquelbytes.grapeguardapi.Controller;

import com.raquelbytes.grapeguardapi.Model.Vinedo;
import com.raquelbytes.grapeguardapi.Repository.VinedoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class VinedoController {

    @Autowired
    private VinedoRepository VinedoRepository;

    // Métodos para la entidad Viñedo

    @GetMapping("/vinedos")
    public List<Vinedo> obtenerVinedos() {
        return VinedoRepository.findAll();
    }

    @GetMapping("/vinedos/{id}")
    public Vinedo obtenerVinedoPorId(@PathVariable(value = "id") Integer vinedoId) {
        return VinedoRepository.findById(vinedoId)
                .orElseThrow(() -> new RuntimeException("Viñedo no encontrado con ID: " + vinedoId));
    }

    @PostMapping("/vinedos")
    public Vinedo crearVinedo(@RequestBody Vinedo vinedo) {
        return VinedoRepository.save(vinedo);
    }

    @PutMapping("/vinedos/{id}")
    public Vinedo actualizarVinedo(@PathVariable(value = "id") Integer vinedoId,
                                   @RequestBody Vinedo vinedoDetails) {
        Vinedo vinedo = VinedoRepository.findById(vinedoId)
                .orElseThrow(() -> new RuntimeException("Viñedo no encontrado con ID: " + vinedoId));

        vinedo.setNombre(vinedoDetails.getNombre());
        vinedo.setUbicacion(vinedoDetails.getUbicacion());
        vinedo.setVariedadesUva(vinedoDetails.getVariedadesUva());
        vinedo.setFechaPlantacion(vinedoDetails.getFechaPlantacion());
        vinedo.setLatitud(vinedoDetails.getLatitud());
        vinedo.setLongitud(vinedoDetails.getLongitud());

        return VinedoRepository.save(vinedo);
    }

    @DeleteMapping("/vinedos/{id}")
    public void eliminarVinedo(@PathVariable(value = "id") Integer vinedoId) {
        Vinedo vinedo = VinedoRepository.findById(vinedoId)
                .orElseThrow(() -> new RuntimeException("Viñedo no encontrado con ID: " + vinedoId));

        VinedoRepository.delete(vinedo);
    }
}

package com.raquelbytes.grapeguardapi.Controller;

import com.raquelbytes.grapeguardapi.Model.Usuario;
import com.raquelbytes.grapeguardapi.Model.Vinedo;
import com.raquelbytes.grapeguardapi.Repository.UsuarioRepository;
import com.raquelbytes.grapeguardapi.Repository.VinedoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/vinedo")
public class VinedoController {

    @Autowired
    private VinedoRepository vinedoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;


    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Vinedo>> obtenerVinedosPorUsuario(@PathVariable Usuario usuarioId) {
        List<Vinedo> vinedos = vinedoRepository.findByUsuarioId(usuarioId);
        if (!vinedos.isEmpty()) {
            return ResponseEntity.ok(vinedos);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/usuario/{usuarioId}/vinedo/{vinedoId}")
    public ResponseEntity<Vinedo> consultarVinedoPorId(@PathVariable Usuario usuarioId, @PathVariable Integer vinedoId) {
        Vinedo vinedo = vinedoRepository.findByIdAndUsuarioId(usuarioId, vinedoId);
        if (vinedo != null) {
            return ResponseEntity.ok(vinedo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/usuario/{usuarioId}/vinedo/nombre/{nombre}")
    public ResponseEntity<Vinedo> consultarVinedoPorNombre(@PathVariable Usuario usuarioId, @PathVariable String nombre) {
        Vinedo vinedo = vinedoRepository.findByUsuarioIdAndNombre(usuarioId, nombre);
        if (vinedo != null) {
            return ResponseEntity.ok(vinedo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    // Añadir nuevos viñedos
    @PostMapping("/usuario/{usuarioId}/nuevo")
    public ResponseEntity<String> agregarNuevoVinedo(@PathVariable Integer usuarioId, @RequestBody Vinedo nuevoVinedo) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + usuarioId));

        nuevoVinedo.setUsuario(usuario);

        vinedoRepository.save(nuevoVinedo);
        return ResponseEntity.ok("Viñedo añadido correctamente");
    }


    // Borrar viñedos (en cascada)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> borrarVinedo(@PathVariable Integer id) {
        if (vinedoRepository.existsById(id)) {
            vinedoRepository.deleteById(id);
            return ResponseEntity.ok("Tarea eliminada correctamente");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

   /* @PutMapping("/{id}")
    public ResponseEntity<String> modificarVinedo(@PathVariable Integer id, @RequestBody Vinedo vinedoModificado) {
        // Buscar el viñedo por su ID
        Optional<Vinedo> optionalVinedo = vinedoRepository.findById(id);

        // Verificar si el viñedo existe
        if (optionalVinedo.isPresent()) {
            // Si existe, actualizar los detalles del viñedo
            Vinedo vinedo = optionalVinedo.get();
            vinedo.setNombre(vinedoModificado.getNombre());
            vinedo.setUbicacion(vinedoModificado.getUbicacion());
            vinedo.setFechaPlantacion(vinedoModificado.getFechaPlantacion());
            vinedo.setHectareas(vinedoModificado.getHectareas());

            // Guardar el viñedo actualizado
            vinedoRepository.save(vinedo);

            // Devolver una respuesta de éxito
            return ResponseEntity.ok("Viñedo modificado correctamente");
        } else {
            // Si no se encuentra el viñedo, devolver una respuesta de no encontrado
            return ResponseEntity.notFound().build();
        }
    } */
    //Sumatorio de hectareas
    public ResponseEntity<List<Vinedo>> obtenerSumHectareasPorUsuario(@PathVariable Usuario idUsuario) {
        BigDecimal sumHectareas = vinedoRepository.sumHectareasByUsuarioId(idUsuario);
        if (sumHectareas != null) {
            return null;
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}


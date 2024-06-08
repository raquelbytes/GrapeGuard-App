package com.raquelbytes.grapeguardapi.Controller;

import com.raquelbytes.grapeguardapi.DTO.VinedoDTO;
import com.raquelbytes.grapeguardapi.Model.Usuario;
import com.raquelbytes.grapeguardapi.Model.Vinedo;
import com.raquelbytes.grapeguardapi.Repository.UsuarioRepository;
import com.raquelbytes.grapeguardapi.Repository.VinedoRepository;
import com.raquelbytes.grapeguardapi.ApiMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(ApiMap.VINEDO_BASE)
public class VinedoController {

    @Autowired
    private VinedoRepository vinedoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    public List<VinedoDTO> listarVinedos() {
        return vinedoRepository.findAll().stream().map(VinedoDTO::converter).collect(Collectors.toList());
    }

    @GetMapping(ApiMap.VINEDO_USUARIO_ID)
    public ResponseEntity<List<VinedoDTO>> obtenerVinedosPorUsuario(@PathVariable Integer usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado con ID: " + usuarioId));
        List<Vinedo> vinedos = vinedoRepository.findByUsuario(usuario);
        if (!vinedos.isEmpty()) {
            List<VinedoDTO> vinedoDTOs = vinedos.stream().map(VinedoDTO::converter).collect(Collectors.toList());
            return ResponseEntity.ok(vinedoDTOs);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(ApiMap.VINEDO_ID)
    public ResponseEntity<VinedoDTO> obtenerVinedoPorId(@PathVariable Integer id) {
        Vinedo vinedo = vinedoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Viñedo no encontrado con ID: " + id));
        return ResponseEntity.ok(VinedoDTO.converter(vinedo));
    }

    @PostMapping(ApiMap.VINEDO_USUARIO_NUEVO)
    public ResponseEntity<Object> agregarNuevoVinedo(@PathVariable Integer usuarioId, @RequestBody VinedoDTO nuevoVinedoDTO) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado con ID: " + usuarioId));

        if (vinedoRepository. existsByNombreAndUbicacion(nuevoVinedoDTO.getNombre(), nuevoVinedoDTO.getUbicacion())) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Ya existe un viñedo en esta ubicación");
            return ResponseEntity.badRequest().body(response);
        }

        Vinedo nuevoVinedo = new Vinedo();
        nuevoVinedo.setUsuario(usuario);
        nuevoVinedo.setNombre(nuevoVinedoDTO.getNombre());
        nuevoVinedo.setUbicacion(nuevoVinedoDTO.getUbicacion());
        nuevoVinedo.setFechaPlantacion(nuevoVinedoDTO.getFechaPlantacion());
        nuevoVinedo.setHectareas(nuevoVinedoDTO.getHectareas());

        Vinedo savedVinedo = vinedoRepository.save(nuevoVinedo);
        return ResponseEntity.ok().build();
    }


    @PutMapping(ApiMap.VINEDO_MODIFICAR)
    public ResponseEntity<VinedoDTO> modificarVinedo(@PathVariable Integer id, @RequestBody VinedoDTO vinedoModificadoDTO) {
        Vinedo vinedo = vinedoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Viñedo no encontrado con ID: " + id));

        vinedo.setNombre(vinedoModificadoDTO.getNombre());
        vinedo.setUbicacion(vinedoModificadoDTO.getUbicacion());
        vinedo.setFechaPlantacion(vinedoModificadoDTO.getFechaPlantacion());
        vinedo.setHectareas(vinedoModificadoDTO.getHectareas());

        Vinedo updatedVinedo = vinedoRepository.save(vinedo);
        return ResponseEntity.ok(VinedoDTO.converter(updatedVinedo));
    }

    @DeleteMapping(ApiMap.VINEDO_ELIMINAR)
    public ResponseEntity<Void> borrarVinedo(@PathVariable Integer id) {
        Vinedo vinedo = vinedoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Viñedo no encontrado con ID: " + id));
        vinedoRepository.delete(vinedo);
        return ResponseEntity.ok().build();
    }

    @GetMapping(ApiMap.VINEDO_SUMA_HECTAREAS)
    public ResponseEntity<BigDecimal> obtenerSumHectareasPorUsuario(@PathVariable Integer usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado con ID: " + usuarioId));
        BigDecimal sumHectareas = vinedoRepository.sumHectareasByUsuarioId(usuario);
        if (sumHectareas != null) {
            return ResponseEntity.ok(sumHectareas);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
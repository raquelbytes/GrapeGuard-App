package com.raquelbytes.grapeguardapi.Controller;

import com.raquelbytes.grapeguardapi.Model.Tratamiento;
import com.raquelbytes.grapeguardapi.Model.Usuario;
import com.raquelbytes.grapeguardapi.Model.Vinedo;
import com.raquelbytes.grapeguardapi.Repository.TratamientoRepository;
import com.raquelbytes.grapeguardapi.Repository.UsuarioRepository;
import com.raquelbytes.grapeguardapi.Repository.VinedoRepository;
import com.raquelbytes.grapeguardapi.Repository.VinedoTratamientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tratamiento")
public class TratamientoController {

    @Autowired
    private TratamientoRepository tratamientoRepository;
    @Autowired
    private VinedoTratamientoRepository vinedoTratamientoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    // Añadir tratamiento general
    @PostMapping("/nuevo/{idUsuario}")
    public ResponseEntity<Tratamiento> agregarTratamientoGeneral(@PathVariable Integer idUsuario, @RequestBody Tratamiento nuevoTratamiento) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + idUsuario));

        nuevoTratamiento.setUsuario(usuario);
        Tratamiento tratamientoGuardado = tratamientoRepository.save(nuevoTratamiento);
        return ResponseEntity.ok(tratamientoGuardado);
    }



   /* // Modificar tratamiento
    @PutMapping("/{id}")
    public ResponseEntity<Tratamiento> modificarTratamiento(@PathVariable Integer id, @RequestBody Tratamiento tratamientoModificado) {
        if (tratamientoRepository.existsById(id)) {
            tratamientoModificado.setId(id);
            Tratamiento tratamientoActualizado = tratamientoRepository.save(tratamientoModificado);
            return ResponseEntity.ok(tratamientoActualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    } */


    // Borrar tratamiento (comprobando asociaciones primero)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> borrarTratamiento(@PathVariable Integer id) {
        if (tratamientoRepository.existsById(id)) {
            // Verificar si existen referencias en VinedoTratamiento
            if (vinedoTratamientoRepository.existsByTratamientoId(id)) {
                vinedoTratamientoRepository.deleteByTratamientoId(id);
                tratamientoRepository.deleteById(id);
                return ResponseEntity.ok("Tratamiento y referencias en VinedoTratamiento eliminados correctamente");
            } else {
                // Si no hay referencias, se puede borrar el tratamiento
                tratamientoRepository.deleteById(id);
                return ResponseEntity.ok("Tratamiento eliminado correctamente");
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{idUsuario}/tratamientos")
    public ResponseEntity<List<Tratamiento>> consultarTratamientosPorUsuario(@PathVariable Integer idUsuario) {

        Optional<Usuario> usuarioOptional = usuarioRepository.findById(idUsuario);

        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            // Luego, obtén los tratamientos asociados a ese usuario
            List<Tratamiento> tratamientos = tratamientoRepository.findByidUsuario(idUsuario);
            if (!tratamientos.isEmpty()) {
                return ResponseEntity.ok(tratamientos);
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}

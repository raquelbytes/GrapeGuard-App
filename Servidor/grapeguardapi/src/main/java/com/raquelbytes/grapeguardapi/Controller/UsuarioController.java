package com.raquelbytes.grapeguardapi.Controller;

import com.raquelbytes.grapeguardapi.Model.Usuario;
import com.raquelbytes.grapeguardapi.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String email, @RequestParam String contrasena) {
        Usuario usuario = usuarioRepository.findByEmailAndContrasena(email, contrasena);
        if (usuario != null) {
            return ResponseEntity.ok("Login exitoso");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
        }
    }


    @PostMapping("/registro")
    public ResponseEntity<String> registro(@RequestBody Usuario nuevoUsuario) {
        if (usuarioRepository.existsByEmail(nuevoUsuario.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El correo electrónico ya está registrado");
        } else {
            usuarioRepository.save(nuevoUsuario);
            return ResponseEntity.ok("Usuario registrado correctamente");
        }
    }
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<Usuario> obtenerUsuarioPorId(@PathVariable Integer usuarioId) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(usuarioId);
        if (usuarioOptional.isPresent()) {
            return ResponseEntity.ok(usuarioOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }




}

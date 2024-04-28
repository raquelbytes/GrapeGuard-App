package com.raquelbytes.grapeguardapi.Controller;

import com.raquelbytes.grapeguardapi.Model.Usuario;
import com.raquelbytes.grapeguardapi.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;
    // Obtener todos los usuarios
    @GetMapping("/")
    public String Hola() {
        return "Hola Buenas";
    }
    // Obtener todos los usuarios
    @GetMapping("/usuarios")
    public List<Usuario> obtenerUsuarios() {
        return usuarioRepository.findAll();
    }

    // Obtener un usuario por su ID
    @GetMapping("/usuarios/{id}")
    public Usuario obtenerUsuarioPorId(@PathVariable(value = "id") Integer usuarioId) {
        return usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + usuarioId));
    }

    // Crear un nuevo usuario
    @PostMapping("/usuarios")
    public Usuario crearUsuario(@RequestBody Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    // Actualizar un usuario existente
    @PutMapping("/usuarios/{id}")
    public Usuario actualizarUsuario(@PathVariable(value = "id") Integer usuarioId,
                                     @RequestBody Usuario usuarioDetails) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + usuarioId));

        usuario.setNombre(usuarioDetails.getNombre());
        usuario.setEmail(usuarioDetails.getEmail());
        usuario.setContrasena(usuarioDetails.getContrasena());

        return usuarioRepository.save(usuario);
    }

    // Eliminar un usuario
    @DeleteMapping("/usuarios/{id}")
    public void eliminarUsuario(@PathVariable(value = "id") Integer usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + usuarioId));

        usuarioRepository.delete(usuario);
    }
}

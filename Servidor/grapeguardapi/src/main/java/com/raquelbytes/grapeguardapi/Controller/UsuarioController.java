package com.raquelbytes.grapeguardapi.Controller;

import com.raquelbytes.grapeguardapi.ApiMap;
import com.raquelbytes.grapeguardapi.DTO.UsuarioDTO;
import com.raquelbytes.grapeguardapi.Model.Usuario;
import com.raquelbytes.grapeguardapi.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(ApiMap.USUARIO_BASE)
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @GetMapping
    public List<UsuarioDTO> getUsuarios(){
        return usuarioRepository.findAll().stream().map(UsuarioDTO::converter).collect(Collectors.toList());
    }


    @PostMapping(ApiMap.USUARIO_LOGIN)
    public ResponseEntity<Object> login(@RequestBody Usuario usuario) {
        String email = usuario.getEmail();
        String contrasena = usuario.getContrasena();

        Usuario usuarioEncontrado = usuarioRepository.findByEmailAndContrasena(email, contrasena);
        if (usuarioEncontrado != null) {

            Map<String, Object> response = new HashMap<>();
            response.put("mensaje", "Login exitoso");
            response.put("usuario", usuarioEncontrado);


            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
        }
    }

    @GetMapping(ApiMap.USUARIO_POR_ID)
    public ResponseEntity<UsuarioDTO> obtenerUsuarioPorId(@PathVariable Integer ID_usuario) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(ID_usuario);
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            UsuarioDTO usuarioDTO = UsuarioDTO.converter(usuario);
            return ResponseEntity.ok(usuarioDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }



    @PostMapping(ApiMap.USUARIO_REGISTER)
    public ResponseEntity<String> registro(@RequestBody Usuario nuevoUsuario) {
        if (usuarioRepository.existsByEmail(nuevoUsuario.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El correo electrónico ya está registrado");
        } else {
            usuarioRepository.save(nuevoUsuario);
            return ResponseEntity.ok("Usuario registrado correctamente");
        }
    }
    @PutMapping(ApiMap.USUARIO_MODIFICAR)
    public String modificarUsuario(@PathVariable Integer ID_usuario ,@RequestBody Usuario usuario){
        Usuario usuarioModificado = usuarioRepository.findById(ID_usuario).get();

        usuarioModificado.setNombre(usuario.getNombre());
        usuarioModificado.setApellido(usuario.getApellido());
        usuarioModificado.setContrasena(usuario.getContrasena());
        usuarioModificado.setEmail(usuario.getEmail());
        usuarioModificado.setFoto(usuario.getFoto());
        usuarioRepository.save(usuarioModificado);
        return "Usuario Modificado Correctamente";
    }
    @DeleteMapping(ApiMap.USUARIO_ELIMINAR)
    public String borrarUsuario(@PathVariable Integer ID_usuario){
        String nombre = usuarioRepository.findById(ID_usuario).get().getNombre();
        usuarioRepository.delete(usuarioRepository.findById(ID_usuario).get());
        return "El Usuario " + nombre + " ha sido eliminado";
    }




}

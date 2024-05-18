package com.raquelbytes.grapeguardapi.DTO;


import com.raquelbytes.grapeguardapi.Model.Usuario;
import lombok.Data;


@Data
public class UsuarioDTO {
    private Integer ID_usuario;
    private String nombre;
    private String apellido;
    private String email;
    private String contrasena;
    private String foto;

    public static UsuarioDTO converter(Usuario source){
        UsuarioDTO target = new UsuarioDTO();
        target.setID_usuario(source.getID_usuario());
        target.setNombre(source.getNombre());
        target.setApellido(source.getApellido());
        target.setEmail(source.getEmail());
        target.setContrasena(source.getContrasena());
        target.setFoto(source.getFoto());
        return target;
    }
    public static Usuario fromEntity(UsuarioDTO source){
        Usuario target = new Usuario();
        target.setNombre(source.getNombre());
        target.setApellido(source.getApellido());
        target.setEmail(source.getEmail());
        target.setContrasena(source.getContrasena());
        target.setFoto(source.getFoto());
        return target;
    }

}
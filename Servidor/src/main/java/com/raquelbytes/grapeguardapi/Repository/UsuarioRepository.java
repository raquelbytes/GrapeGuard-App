package com.raquelbytes.grapeguardapi.Repository;

import com.raquelbytes.grapeguardapi.Model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario,Integer> {

}

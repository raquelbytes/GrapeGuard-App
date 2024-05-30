/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.dao;

/**
 *
 * @author raquel
 */

import java.util.List;
import modelo.vo.Usuario;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class UsuarioDAO {

    public Usuario getUsuario(Session session, int usuarioId) throws Exception {
        // Importante: El método get busca por la clave primaria
        return session.get(Usuario.class, usuarioId);
    }

    public Usuario buscarUsuario(Session session, int usuarioId) throws Exception {
        Usuario u = null;
        Query q = session.createQuery("from Usuario u where u.id = :usuarioId");
        q.setParameter("usuarioId", usuarioId);
        u = (Usuario) q.uniqueResult();
        return u;
    }

    public List<Usuario> getAllUsuarios(Session session) throws Exception {
        Query q = session.createQuery("from Usuario");
        return q.list();
    }
}


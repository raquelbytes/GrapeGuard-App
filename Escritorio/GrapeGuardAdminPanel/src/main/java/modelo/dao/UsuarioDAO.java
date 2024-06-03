/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.dao;

/**
 *
 * @author raquel
 */

import java.util.Iterator;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
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
    
        public void cargarCombo(Session session, DefaultComboBoxModel modeloCombo) throws Exception {
        modeloCombo.removeAllElements();
        Usuario u;
        Query q = session.createQuery("from Usuario u");

        List<Usuario> listaVinedos = q.list(); 
        Iterator it = listaVinedos.iterator();
      

        while (it.hasNext()) {
            System.out.println(it);
            modeloCombo.addElement(it.next());
        }

    }
}


package modelo.dao;

/**
 *
 * @author raquel
 */

import java.util.Iterator;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;
import modelo.vo.Usuario;
import modelo.vo.Vinedo;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class VinedoDAO {

public Long sumarHectareasPorUsuario(Session session, int idUsuario) throws Exception {
    Long sumaHectareas = null;
    Query q = session.createQuery("SELECT SUM(v.hectareas) FROM Vinedo v WHERE v.usuario.id = :idUsuario");
    q.setParameter("idUsuario", idUsuario);
    
   
    Number resultado = (Number) q.uniqueResult();
    if (resultado != null) {
        sumaHectareas = resultado.longValue();
  
    }
    else{
     sumaHectareas = Long.parseLong("0");}
  
    return sumaHectareas;
}




    public void borrarVinedos(Session session, List<Vinedo> listaVinedos)throws Exception {
        for (Vinedo v : listaVinedos) {
            session.delete(v);
        }
    }

    public Vinedo getVinedo(Session session, int vinedoId) throws Exception {
        return session.get(Vinedo.class, vinedoId);
    }

    public Vinedo buscarVinedo(Session session, int vinedoId) throws Exception {
        Query q = session.createQuery("from Vinedo v where v.id = :vinedoId");
        q.setParameter("vinedoId", vinedoId);
        return (Vinedo) q.uniqueResult();
    }

    public void insertar(Session session, Vinedo vinedo) throws Exception {
     
        session.save(vinedo);
    }

    public void borrar(Session session, Vinedo v) throws Exception {
        session.delete(v);
    }

    public Vinedo modificar(Session session, Vinedo v, String nombre, String ubicacion, java.util.Date fechaPlantacion, String hectareas) throws Exception {
       
        if(!nombre.isEmpty()){ v.setNombre(nombre);}
        if(!ubicacion.isEmpty()){   v.setUbicacion(ubicacion);}
        if(fechaPlantacion != null){v.setFechaPlantacion(fechaPlantacion);}
         if(!hectareas.isEmpty()){
         Double hect = Double.valueOf(hectareas); 
         v.setHectareas(hect);}
        session.update(v);
        return v;
    }
     public void cargarCombo(Session session, DefaultComboBoxModel modeloCombo) throws Exception {
        modeloCombo.removeAllElements();
         Vinedo V;
        Query q = session.createQuery("from Vinedo v");

        List<Vinedo> listaVinedos = q.list(); 
        Iterator it = listaVinedos.iterator();

        while (it.hasNext()) {
            modeloCombo.addElement(it.next());
        }

    }
     
    public List<Vinedo> getAllVinedos(Session session) {
        return session.createQuery("from Vinedo", Vinedo.class).list();
}
    
    public List<Vinedo> getAllVinedosByUsuario(Session session, int idUsuario) {
    return session.createQuery("from Vinedo where ID_usuario = :idUsuario", Vinedo.class)
                  .setParameter("idUsuario", idUsuario)
                  .list();
}

}


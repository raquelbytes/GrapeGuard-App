package modelo.dao;

/**
 *
 * @author raquel
 */

import java.util.Iterator;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import modelo.vo.Usuario;
import modelo.vo.Vinedo;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class VinedoDAO {

    public long cuentaVinedosDeUsuario(Session session, int usuarioId) throws Exception {
        Query q = session.createQuery("select count(*) from Vinedo v where v.usuario.id = :usuarioId");
        q.setParameter("usuarioId", usuarioId);
        return (long) q.uniqueResult();
    }

    public void borrarVinedos(Session session, List<Vinedo> listaVinedos) {
        for (Vinedo v : listaVinedos) {
            session.delete(v);
        }
    }

    public void cargarTabla(Session session, Usuario usuario, DefaultTableModel modeloTabla) {
        Vinedo v;
        modeloTabla.setRowCount(0);
        Iterator<Vinedo> it = usuario.getVinedos().iterator();
        while (it.hasNext()) {
            modeloTabla.setRowCount(modeloTabla.getRowCount() + 1);
            v = it.next();
            modeloTabla.setValueAt(v.getId(), modeloTabla.getRowCount() - 1, 0);
            modeloTabla.setValueAt(v.getNombre(), modeloTabla.getRowCount() - 1, 1);
            modeloTabla.setValueAt(v.getUbicacion(), modeloTabla.getRowCount() - 1, 2);
            modeloTabla.setValueAt(v.getFechaPlantacion(), modeloTabla.getRowCount() - 1, 3);
            modeloTabla.setValueAt(v.getHectareas(), modeloTabla.getRowCount() - 1, 4);
        }
    }

    public void recargarTabla(Session session, Usuario usuario, DefaultTableModel modeloTabla) {
        Vinedo v;
        modeloTabla.setRowCount(0);

        Query q = session.createQuery("from Vinedo v where v.usuario.id = :usuarioId");
        q.setParameter("usuarioId", usuario.getId());

        Iterator<Vinedo> it = q.list().iterator();

        while (it.hasNext()) {
            modeloTabla.setRowCount(modeloTabla.getRowCount() + 1);
            v = it.next();
            modeloTabla.setValueAt(v.getId(), modeloTabla.getRowCount() - 1, 0);
            modeloTabla.setValueAt(v.getNombre(), modeloTabla.getRowCount() - 1, 1);
            modeloTabla.setValueAt(v.getUbicacion(), modeloTabla.getRowCount() - 1, 2);
            modeloTabla.setValueAt(v.getFechaPlantacion(), modeloTabla.getRowCount() - 1, 3);
            modeloTabla.setValueAt(v.getHectareas(), modeloTabla.getRowCount() - 1, 4);
        }
    }

    public Vinedo getVinedo(Session session, int vinedoId) throws Exception {
        return session.get(Vinedo.class, vinedoId);
    }

    public Vinedo buscarVinedo(Session session, int vinedoId) {
        Query q = session.createQuery("from Vinedo v where v.id = :vinedoId");
        q.setParameter("vinedoId", vinedoId);
        return (Vinedo) q.uniqueResult();
    }

    public void insertar(Session session, Usuario usuario, String nombre, String ubicacion, java.util.Date fechaPlantacion, double hectareas) {
        Vinedo v = new Vinedo(usuario, nombre, ubicacion, fechaPlantacion, hectareas);
        session.save(v);
    }

    public void borrar(Session session, Vinedo v) {
        session.delete(v);
    }

    public void modificar(Session session, Vinedo v, String nombre, String ubicacion, java.util.Date fechaPlantacion, double hectareas) {
        v.setNombre(nombre);
        v.setUbicacion(ubicacion);
        v.setFechaPlantacion(fechaPlantacion);
        v.setHectareas(hectareas);
        session.update(v);
    }
}

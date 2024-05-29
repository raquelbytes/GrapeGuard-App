
package modelo.dao;

/**
 *
 * @author raquel
 */


import java.util.Iterator;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import modelo.vo.Tratamiento;
import modelo.vo.Usuario;
import org.hibernate.Session;
import org.hibernate.query.Query;


public class TratamientoDAO {

    public long cuentaTratamientosDeUsuario(Session session, int usuarioId) throws Exception {
        Query q = session.createQuery("select count(*) from Tratamiento t where t.usuario.ID_usuario = :usuarioId");
        q.setParameter("usuarioId", usuarioId);
        return (long) q.uniqueResult();
    }

    public void borrarTratamientos(Session session, List<Tratamiento> listaTratamientos) {
        for (Tratamiento t : listaTratamientos) {
            session.delete(t);
        }
    }

    public void cargarTabla(Session session, Usuario usuario, DefaultTableModel modeloTabla) {
        Tratamiento t;
        modeloTabla.setRowCount(0);
        Iterator<Tratamiento> it = usuario.getTratamientos().iterator();
        while (it.hasNext()) {
            modeloTabla.setRowCount(modeloTabla.getRowCount() + 1);
            t = it.next();
            modeloTabla.setValueAt(t.getId(), modeloTabla.getRowCount() - 1, 0);
            modeloTabla.setValueAt(t.getNombre(), modeloTabla.getRowCount() - 1, 1);
            modeloTabla.setValueAt(t.getCantidad(), modeloTabla.getRowCount() - 1, 2);
            modeloTabla.setValueAt(t.getPrecioUnitario(), modeloTabla.getRowCount() - 1, 3);
            modeloTabla.setValueAt(t.isEnPosesion(), modeloTabla.getRowCount() - 1, 4);
        }
    }

    public void recargarTabla(Session session, Usuario usuario, DefaultTableModel modeloTabla) {
        Tratamiento t;
        modeloTabla.setRowCount(0);

        Query q = session.createQuery("from Tratamiento t where t.usuario.ID_usuario = :usuarioId");
        q.setParameter("usuarioId", usuario.getId());

        Iterator<Tratamiento> it = q.list().iterator();

        while (it.hasNext()) {
            modeloTabla.setRowCount(modeloTabla.getRowCount() + 1);
            t = it.next();
            modeloTabla.setValueAt(t.getId(), modeloTabla.getRowCount() - 1, 0);
            modeloTabla.setValueAt(t.getNombre(), modeloTabla.getRowCount() - 1, 1);
            modeloTabla.setValueAt(t.getCantidad(), modeloTabla.getRowCount() - 1, 2);
            modeloTabla.setValueAt(t.getPrecioUnitario(), modeloTabla.getRowCount() - 1, 3);
            modeloTabla.setValueAt(t.isEnPosesion(), modeloTabla.getRowCount() - 1, 4);
        }
    }

    public Tratamiento getTratamiento(Session session, int tratamientoId) throws Exception {
        return session.get(Tratamiento.class, tratamientoId);
    }

    public Tratamiento buscarTratamiento(Session session, int tratamientoId) {
        Query q = session.createQuery("from Tratamiento t where t.ID_tratamiento = :tratamientoId");
        q.setParameter("tratamientoId", tratamientoId);
        return (Tratamiento) q.uniqueResult();
    }

    public void insertar(Session session, Usuario usuario, String nombre, int cantidad, double precioUnitario, boolean enPosesion) {
        Tratamiento t = new Tratamiento();
        t.setUsuario(usuario);
        t.setNombre(nombre);
        t.setCantidad(cantidad);
        t.setPrecioUnitario(precioUnitario);
        t.setEnPosesion(enPosesion);
        session.save(t);
    }

    public void borrar(Session session, Tratamiento t) {
        session.delete(t);
    }

    public void modificar(Session session, Tratamiento t, String nombre, int cantidad, double precioUnitario, boolean enPosesion) {
        t.setNombre(nombre);
        t.setCantidad(cantidad);
        t.setPrecioUnitario(precioUnitario);
        t.setEnPosesion(enPosesion);
        session.update(t);
    }
}


package modelo.dao;

/**
 *
 * @author raquel
 */
import java.util.Iterator;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
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

    public Tratamiento getTratamiento(Session session, int tratamientoId) throws Exception {
        return session.get(Tratamiento.class, tratamientoId);
    }

    public Tratamiento buscarTratamiento(Session session, int tratamientoId) {
        Query q = session.createQuery("from Tratamiento t where t.ID_tratamiento = :tratamientoId");
        q.setParameter("tratamientoId", tratamientoId);
        return (Tratamiento) q.uniqueResult();
    }

    public void insertar(Session session, Tratamiento t) throws Exception {

        session.save(t);
    }

    public void borrar(Session session, Tratamiento t) {
        session.delete(t);
    }

    public Tratamiento modificar(Session session, Tratamiento t, String nombre, String cantidad, String precioUnitario) throws Exception {

        if (!nombre.isEmpty()) {
            t.setNombre(nombre);
        }
        if (!cantidad.isEmpty()) {
            int cant = Integer.valueOf(cantidad);
            t.setCantidad(cant);
        }

        if (!precioUnitario.isEmpty()) {
            Double precioUn = Double.valueOf(precioUnitario);
            t.setPrecioUnitario(precioUn);
        }
        session.update(t);
        return t;

    }

    public List<Tratamiento> getAllTratamientos(Session session) {
        return session.createQuery("from Tratamiento", Tratamiento.class).list();
    }

    public void cargarCombo(Session session, DefaultComboBoxModel modeloCombo) throws Exception {
        modeloCombo.removeAllElements();
        Tratamiento t;
        Query q = session.createQuery("from Tratamiento t");

        List<Tratamiento> listaTratamientos = q.list();
        Iterator it = listaTratamientos.iterator();

        while (it.hasNext()) {
            System.out.println(it);
            modeloCombo.addElement(it.next());
        }

    }
    
    public Tratamiento existeTratamientoConMismoNombreYPrecio(Session session, String nombre, Double precioUnitario) throws Exception {
    Query<Tratamiento> q = session.createQuery("FROM Tratamiento t WHERE t.nombre = :nombre AND t.precioUnitario = :precioUnitario", Tratamiento.class);
    q.setParameter("nombre", nombre);
    q.setParameter("precioUnitario", precioUnitario);
    return q.uniqueResult();
}


}

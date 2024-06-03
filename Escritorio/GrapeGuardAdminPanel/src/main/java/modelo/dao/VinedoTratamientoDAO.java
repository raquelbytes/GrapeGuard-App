package modelo.dao;

/**
 *
 * @author raquel
 */
import java.util.Iterator;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import modelo.vo.Vinedo;
import modelo.vo.VinedoTratamiento;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class VinedoTratamientoDAO {

    public void borrarVinedoTratamientos(Session session, List<VinedoTratamiento> listaVinedoTratamientos) {
        for (VinedoTratamiento vt : listaVinedoTratamientos) {
            session.delete(vt);
        }
    }

    public VinedoTratamiento getVinedoTratamiento(Session session, int vinedoTratamientoId) throws Exception {
        return session.get(VinedoTratamiento.class, vinedoTratamientoId);
    }

    public VinedoTratamiento buscarVinedoTratamiento(Session session, int vinedoTratamientoId) {
        Query q = session.createQuery("from Vinedo_Tratamiento vt where vt.ID_vinedo_tratamiento = :vinedoTratamientoId");
        q.setParameter("vinedoTratamientoId", vinedoTratamientoId);
        return (VinedoTratamiento) q.uniqueResult();
    }

    public List<VinedoTratamiento> getTratamientosByVinedo(Session session, int ID_vinedo) {
        Query<VinedoTratamiento> q = session.createQuery("from VinedoTratamiento vt where vt.vinedo.id = :ID_vinedo", VinedoTratamiento.class);
        q.setParameter("ID_vinedo", ID_vinedo);

        List<VinedoTratamiento> listaVinedosTrat = q.list();
        Iterator<VinedoTratamiento> it = listaVinedosTrat.iterator();

        while (it.hasNext()) {
            System.out.println("Prueba " + it.next());
        }
        return listaVinedosTrat;
    }

    public VinedoTratamiento insertar(Session session, VinedoTratamiento vinedoTrat) throws Exception {

        session.save(vinedoTrat);
        return vinedoTrat;
    }

    public void borrar(Session session, VinedoTratamiento v) throws Exception {
        session.delete(v);
    }
}

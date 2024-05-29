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

    public void cargarTabla(Session session, Vinedo vinedo, DefaultTableModel modeloTabla) {
        VinedoTratamiento vt;
        modeloTabla.setRowCount(0);
        Iterator<VinedoTratamiento> it = vinedo.getTratamientos().iterator();
        while (it.hasNext()) {
            modeloTabla.setRowCount(modeloTabla.getRowCount() + 1);
            vt = it.next();
            modeloTabla.setValueAt(vt.getId(), modeloTabla.getRowCount() - 1, 0);
            modeloTabla.setValueAt(vt.getTratamiento().getNombre(), modeloTabla.getRowCount() - 1, 1);
            modeloTabla.setValueAt(vt.getCantidad(), modeloTabla.getRowCount() - 1, 2);
            modeloTabla.setValueAt(vt.getFechaCompra(), modeloTabla.getRowCount() - 1, 3);
            modeloTabla.setValueAt(vt.isEnPosesion(), modeloTabla.getRowCount() - 1, 4);
        }
    }

    public void recargarTabla(Session session, Vinedo vinedo, DefaultTableModel modeloTabla) {
        VinedoTratamiento vt;
        modeloTabla.setRowCount(0);

        Query q = session.createQuery("from Vinedo_Tratamiento vt where vt.vinedo.ID_vinedo = :vinedoId");
        q.setParameter("vinedoId", vinedo.getId());

        Iterator<VinedoTratamiento> it = q.list().iterator();

        while (it.hasNext()) {
            modeloTabla.setRowCount(modeloTabla.getRowCount() + 1);
            vt = it.next();
            modeloTabla.setValueAt(vt.getId(), modeloTabla.getRowCount() - 1, 0);
            modeloTabla.setValueAt(vt.getTratamiento().getNombre(), modeloTabla.getRowCount() - 1, 1);
            modeloTabla.setValueAt(vt.getCantidad(), modeloTabla.getRowCount() - 1, 2);
            modeloTabla.setValueAt(vt.getFechaCompra(), modeloTabla.getRowCount() - 1, 3);
            modeloTabla.setValueAt(vt.isEnPosesion(), modeloTabla.getRowCount() - 1, 4);
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

}

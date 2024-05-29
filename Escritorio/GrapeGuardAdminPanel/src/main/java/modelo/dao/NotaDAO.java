
package modelo.dao;

/**
 *
 * @author raquel
 */


import java.util.Iterator;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import modelo.vo.Nota;
import modelo.vo.Vinedo;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class NotaDAO {

    public long cuentaNotasDeVinedo(Session session, int vinedoId) throws Exception {
        Query q = session.createQuery("select count(*) from Nota n where n.vinedo.id = :vinedoId");
        q.setParameter("vinedoId", vinedoId);
        return (long) q.uniqueResult();
    }

    public void borrarNotas(Session session, List<Nota> listaNotas) {
        for (Nota n : listaNotas) {
            session.delete(n);
        }
    }

    public void cargarTabla(Session session, Vinedo vinedo, DefaultTableModel modeloTabla) {
        Nota n;
        modeloTabla.setRowCount(0);
        Iterator<Nota> it = vinedo.getNotas().iterator();
        while (it.hasNext()) {
            modeloTabla.setRowCount(modeloTabla.getRowCount() + 1);
            n = it.next();
            modeloTabla.setValueAt(n.getId(), modeloTabla.getRowCount() - 1, 0);
            modeloTabla.setValueAt(n.getNota(), modeloTabla.getRowCount() - 1, 1);
            modeloTabla.setValueAt(n.getFechaCreacion(), modeloTabla.getRowCount() - 1, 2);
            modeloTabla.setValueAt(n.getPrioridad(), modeloTabla.getRowCount() - 1, 3);
        }
    }

    public void recargarTabla(Session session, Vinedo vinedo, DefaultTableModel modeloTabla) {
        Nota n;
        modeloTabla.setRowCount(0);

        Query q = session.createQuery("from Nota n where n.vinedo.id = :vinedoId");
        q.setParameter("vinedoId", vinedo.getId());

        Iterator<Nota> it = q.list().iterator();

        while (it.hasNext()) {
            modeloTabla.setRowCount(modeloTabla.getRowCount() + 1);
            n = it.next();
            modeloTabla.setValueAt(n.getId(), modeloTabla.getRowCount() - 1, 0);
            modeloTabla.setValueAt(n.getNota(), modeloTabla.getRowCount() - 1, 1);
            modeloTabla.setValueAt(n.getFechaCreacion(), modeloTabla.getRowCount() - 1, 2);
            modeloTabla.setValueAt(n.getPrioridad(), modeloTabla.getRowCount() - 1, 3);
        }
    }

    public Nota getNota(Session session, int notaId) throws Exception {
        return session.get(Nota.class, notaId);
    }

    public Nota buscarNota(Session session, int notaId) {
        Query q = session.createQuery("from Nota n where n.id = :notaId");
        q.setParameter("notaId", notaId);
        return (Nota) q.uniqueResult();
    }

    public void insertar(Session session, Vinedo vinedo, String notaTexto, Nota.PrioridadNota prioridad) {
        Nota n = new Nota(vinedo, notaTexto, prioridad);
        session.save(n);
    }

    public void borrar(Session session, Nota n) {
        session.delete(n);
    }

    public void modificar(Session session, Nota n, String notaTexto, Nota.PrioridadNota prioridad) {
        n.setNota(notaTexto);
        n.setPrioridad(prioridad);
        session.update(n);
    }
}


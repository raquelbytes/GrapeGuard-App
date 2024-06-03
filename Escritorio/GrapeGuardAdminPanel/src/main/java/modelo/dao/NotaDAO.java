
package modelo.dao;

/**
 *
 * @author raquel
 */


import Util.NotaTableModel;
import java.util.Iterator;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import modelo.vo.Nota;
import modelo.vo.Nota.PrioridadNota;
import modelo.vo.Vinedo;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class NotaDAO {

    public long cuentaNotasDeVinedo(Session session, int vinedoId) throws Exception {
       Query q = session.createQuery("select count(*) from Nota n where n.vinedo.id = :vinedoId and n.prioridad = :prioridad");
        q.setParameter("vinedoId", vinedoId);
        q.setParameter("prioridad", PrioridadNota.Alta.name());
        return (long) q.uniqueResult();
    }

    public void borrarNotas(Session session, List<Nota> listaNotas) {
        for (Nota n : listaNotas) {
            session.delete(n);
        }
    }

    public void cargarTabla(Session session, Vinedo vinedo, NotaTableModel modeloTabla) {
        Query<Nota> query = session.createQuery("from Nota n where n.vinedo.id = :vinedoId", Nota.class);
        query.setParameter("vinedoId", vinedo.getId());
        List<Nota> notas = query.list();

        modeloTabla.setNotas(notas);
    }
    
    public List<Nota> getAllNotas(Session session) {
    return session.createQuery("from Nota", Nota.class).list();
}
    public List<Nota> getNotasByVinedo(Session session, Vinedo vinedo) {
    return session.createQuery("from Nota where vinedo = :vinedo", Nota.class)
                  .setParameter("vinedo", vinedo)
                  .list();
}


    public Nota getNota(Session session, int notaId) throws Exception {
        return session.get(Nota.class, notaId);
    }

    public Nota buscarNota(Session session, int notaId) {
        Query q = session.createQuery("from Nota n where n.id = :notaId");
        q.setParameter("notaId", notaId);
        return (Nota) q.uniqueResult();
    }

    public void insertar(Session session, Nota nota) {
        Nota n = nota;
        session.save(n);
    }

    public void borrar(Session session, Nota n) {
        session.delete(n);
    }

    public Nota modificar(Session session, Nota n, Vinedo vinedo,String notaTexto, Nota.PrioridadNota prioridad) {
        if(notaTexto != "" && !notaTexto.isEmpty() && notaTexto.isBlank()){
        n.setNota(notaTexto);}
        n.setVinedo(vinedo);
        n.setPrioridad(prioridad);
        session.update(n);
        return n;
    }
}


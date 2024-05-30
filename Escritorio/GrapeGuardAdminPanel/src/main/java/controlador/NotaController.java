
package controlador;

import controlador.factory.HibernateUtil;
import modelo.dao.NotaDAO;
import modelo.dao.VinedoDAO;
import org.hibernate.Session;
import vista.NotaView;

/**
 *
 * @author raque
 */
public class NotaController {
    public static Session session;
    public static NotaDAO notDAO;
    public static VinedoDAO vinDAO;
    public static NotaView ventana = new NotaView();
    
    public static void iniciar() {
        ventana.setVisible(true);
        ventana.setLocationRelativeTo(null);
    }

    public static void iniciaSession() {
        session = HibernateUtil.getSessionFactory().openSession();
        vinDAO = HibernateUtil.getVinedoDAO();
        notDAO = HibernateUtil.getNotaDAO();
      
    }

    public static void cerrarSession() {
        session.close();       
    }
}

package controlador;

import Util.NotaTableModel;
import controlador.factory.HibernateUtil;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import modelo.dao.NotaDAO;
import modelo.dao.VinedoDAO;
import modelo.vo.Nota;
import modelo.vo.Vinedo;
import org.hibernate.Session;
import vista.MainView;
import vista.NotaView;

public class NotaController {
    public static Session session;
    public static NotaDAO notDAO;
    public static VinedoDAO vinDAO;
    public static NotaView ventana = new NotaView();
    public static DefaultComboBoxModel modeloCombo = new DefaultComboBoxModel();
    public static NotaTableModel modeloTabla;
    
    public static void iniciar() {
        ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ventana.setVisible(true);
        ventana.setLocationRelativeTo(null);
        ventana.getComboVinedo().setModel(modeloCombo);
        modeloTabla = new NotaTableModel(notDAO.getAllNotas(session));
        ventana.getTabla().setModel(modeloTabla);
    }

    public static void iniciaSession() {
        session = HibernateUtil.getSessionFactory().openSession();
        vinDAO = HibernateUtil.getVinedoDAO();
        notDAO = HibernateUtil.getNotaDAO();
    }

    public static void cerrarSession() {
        if (session != null && session.isOpen()) {
            session.close();
        }
    }

    public static void cargarComboVinedos() {
        try {
            HibernateUtil.beginTx(session);
            vinDAO.cargarCombo(session, modeloCombo);
            HibernateUtil.commitTx(session);
        } catch (Exception ex) {
            session.getTransaction().rollback();
            Logger.getLogger(NotaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void insertarNota() {
        Vinedo vinedoSeleccionado = (Vinedo) ventana.getComboVinedo().getSelectedItem();
        String notaTexto = ventana.getTaNota().getText();
        Nota.PrioridadNota prioridad = null;

        if (ventana.getCmbPrioridad().getSelectedItem() != null) {
            try {
                prioridad = Nota.PrioridadNota.valueOf(ventana.getCmbPrioridad().getSelectedItem().toString());
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(null, "La prioridad seleccionada no es válida.", "Error de Prioridad", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        if (vinedoSeleccionado != null && prioridad != null && !notaTexto.isEmpty()) {
            try {
                HibernateUtil.beginTx(session);
                Nota nuevaNota = new Nota(vinedoSeleccionado, notaTexto, prioridad);
                notDAO.insertar(session, nuevaNota);
                modeloTabla.addNota(nuevaNota);
                HibernateUtil.commitTx(session);
                limpiarFormulario();
            } catch (Exception ex) {
                session.getTransaction().rollback();
                Logger.getLogger(NotaController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debe introducir todos los campos", "Mensaje", JOptionPane.WARNING_MESSAGE);
        }
    }

    public static void actualizarNota(Nota nota, int filaSeleccionada) {
        Vinedo vinedoSeleccionado = (Vinedo) ventana.getComboVinedo().getSelectedItem();
        String notaTexto = ventana.getTaNota().getText();
        Nota.PrioridadNota prioridad = null;

        if (ventana.getCmbPrioridad().getSelectedItem() != null) {
            try {
                prioridad = Nota.PrioridadNota.valueOf(ventana.getCmbPrioridad().getSelectedItem().toString());
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(null, "La prioridad seleccionada no es válida.", "Error de Prioridad", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        try {
            HibernateUtil.beginTx(session);
            Nota notamod = notDAO.modificar(session, nota, vinedoSeleccionado, notaTexto, prioridad);
            modeloTabla.updateNota(filaSeleccionada, notamod);
            HibernateUtil.commitTx(session);
              limpiarFormulario();
        } catch (Exception ex) {
            session.getTransaction().rollback();
            Logger.getLogger(NotaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void eliminarNota(Nota nota, int filaSeleccionada) {
        try {
            HibernateUtil.beginTx(session);
            if (nota != null) {
                notDAO.borrar(session, nota);
                modeloTabla.removeNota(filaSeleccionada);
            }
            HibernateUtil.commitTx(session);
              limpiarFormulario();
        } catch (Exception ex) {
            session.getTransaction().rollback();
            Logger.getLogger(NotaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void actualizarCount() {
        try {
            HibernateUtil.beginTx(session);
            Vinedo vinedoSeleccionado = (Vinedo) ventana.getComboVinedo().getSelectedItem();
            if (vinedoSeleccionado != null) {
                Long numVinedos = notDAO.cuentaNotasDeVinedo(session, vinedoSeleccionado.getId());
                ventana.getTxtSuma().setText("El viñedo tiene un total de " + numVinedos + " notas.");
            }
            HibernateUtil.commitTx(session);
        } catch (Exception ex) {
            session.getTransaction().rollback();
            Logger.getLogger(NotaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void limpiarFormulario() {
        ventana.getComboVinedo().setSelectedItem(null);
        ventana.getCmbPrioridad().setSelectedItem(null);
        ventana.getTaNota().setText("");
    }
    public static void cargarNotaEnFormulario(Nota nota) {
    ventana.getComboVinedo().setSelectedItem(nota.getVinedo());
    ventana.getCmbPrioridad().setSelectedItem(nota.getPrioridad());
    ventana.getTaNota().setText(nota.getNota());
}
    public static void actualizarDatos() {
    cargarComboVinedos();
    modeloTabla.actualizarDatos(notDAO.getAllNotas(session));
    actualizarCount();
}


}

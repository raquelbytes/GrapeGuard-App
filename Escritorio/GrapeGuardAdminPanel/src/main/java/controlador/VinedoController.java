package controlador;

import Util.VinedoTableModel;
import controlador.factory.HibernateUtil;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import modelo.dao.UsuarioDAO;
import modelo.dao.VinedoDAO;
import modelo.vo.Usuario;
import modelo.vo.Vinedo;
import org.hibernate.Session;
import vista.MainView;
import vista.VinedoView;

/**
 *
 * @author raque
 */
public class VinedoController {

    public static Session session;
    public static VinedoDAO vinDAO;
    public static UsuarioDAO usDAO;
    public static VinedoView ventana = new VinedoView();
    public static DefaultComboBoxModel modeloCombo = new DefaultComboBoxModel();
    public static VinedoTableModel modeloTabla;

    public static void iniciar() {
        ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ventana.setVisible(true);
        ventana.setLocationRelativeTo(null);
        ventana.getComboUsuario().setModel(modeloCombo);
        modeloTabla = new VinedoTableModel(vinDAO.getAllVinedos(session));
        ventana.getTabla().setModel(modeloTabla);
    }

    public static void iniciaSession() {
        session = HibernateUtil.getSessionFactory().openSession();
        usDAO = HibernateUtil.getUsuarioDAO();
        vinDAO = HibernateUtil.getVinedoDAO();

    }

    public static void cerrarSession() {
        session.close();
    }

    public static void cargarComboUsuarios() {
        try {
            HibernateUtil.beginTx(session);
            usDAO.cargarCombo(session, modeloCombo);
            HibernateUtil.commitTx(session);
            limpiarFormulario();
        } catch (Exception ex) {
            session.getTransaction().rollback();
            Logger.getLogger(VinedoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

  public static void insertarVinedo() {
    Usuario usuarioSeleccionado = (Usuario) ventana.getComboUsuario().getSelectedItem();
    String nombre = ventana.getTxtNombre().getText();
    String ubicacion = ventana.getTxtUbicacion().getText();
    Date fechaPlantacion = ventana.getjDateChooser1().getDate();
    String hectareas = ventana.getTxtHectareas().getText();

    if (usuarioSeleccionado != null && !nombre.isEmpty() && !ubicacion.isEmpty() && fechaPlantacion != null && !hectareas.isEmpty()) {
        try {
            Double hect = Double.valueOf(hectareas);

            // Verificar si ya existe un viñedo con el mismo nombre y ubicación
            HibernateUtil.beginTx(session);
            Vinedo existe = vinDAO.buscarVinedoPorNombreYUbicacion(session, nombre, ubicacion);
            if (existe != null) {
                JOptionPane.showMessageDialog(null, "Ya existe un viñedo con ese nombre y ubicación.", "Error", JOptionPane.ERROR_MESSAGE);
                HibernateUtil.rollbackTx(session);
                return;
            }

            Vinedo nuevoVinedo = new Vinedo(usuarioSeleccionado, nombre, ubicacion, fechaPlantacion, hect);
            vinDAO.insertar(session, nuevoVinedo);
            modeloTabla.addVinedo(nuevoVinedo);
            HibernateUtil.commitTx(session);
            limpiarFormulario();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "El formato de hectáreas es incorrecto. Asegúrese de ingresar un número válido.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            HibernateUtil.rollbackTx(session);
        } catch (Exception ex) {
            session.getTransaction().rollback();
            Logger.getLogger(VinedoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    } else {
        JOptionPane.showMessageDialog(null, "Debe introducir todos los campos", "Mensaje", JOptionPane.WARNING_MESSAGE);
    }
}



    public static void actualizarVinedo(Vinedo vinedo, int filaSeleccionada) {
        try {
            String nombre = ventana.getTxtNombre().getText();
            String ubicacion = ventana.getTxtUbicacion().getText();
            Date fechaPlantacion = ventana.getjDateChooser1().getDate();
            String hectareas = ventana.getTxtHectareas().getText();

            if (!nombre.isEmpty() && !ubicacion.isEmpty() && fechaPlantacion != null && !hectareas.isEmpty()) {
                try {
                    Double hect = Double.valueOf(hectareas);
                    Vinedo existe = vinDAO.buscarVinedoPorNombreYUbicacion(session, nombre, ubicacion);
                    if (existe != null) {
                        JOptionPane.showMessageDialog(null, "Ya existe un viñedo con ese nombre y ubicación.", "Error", JOptionPane.ERROR_MESSAGE);
                        HibernateUtil.rollbackTx(session);
                        return;
                    }

                    HibernateUtil.beginTx(session);
                    Vinedo v = vinDAO.modificar(session, vinedo, nombre, ubicacion, fechaPlantacion, hectareas);
                    modeloTabla.updateVinedo(filaSeleccionada, v);
                    HibernateUtil.commitTx(session);
                      limpiarFormulario();
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "El formato de hectáreas es incorrecto. Asegúrese de ingresar un número válido.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    session.getTransaction().rollback();
                    Logger.getLogger(VinedoController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Debe introducir todos los campos", "Mensaje", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception ex) {
            Logger.getLogger(VinedoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

      public static void eliminarVinedo(Vinedo vinedo, int filaSeleccionada) {
        int confirmacion = JOptionPane.showConfirmDialog(
            null,
            "¿Está seguro de que quiere borrar? Borrar un viñedo supondrá el borrado de todos los elementos asociados.",
            "Confirmar borrado",
            JOptionPane.YES_NO_OPTION
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                HibernateUtil.beginTx(session);
                if (vinedo != null) {
                    vinDAO.borrar(session, vinedo);
                    modeloTabla.removeVinedo(filaSeleccionada);
                }
                HibernateUtil.commitTx(session);
                  limpiarFormulario();
            } catch (Exception ex) {
                session.getTransaction().rollback();
                Logger.getLogger(VinedoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
           
            return;
        }
    }
   public static void actualizarSuma() {
    try {
        HibernateUtil.beginTx(session);
        Usuario usuarioSeleccionado = (Usuario) ventana.getComboUsuario().getSelectedItem();

        if (usuarioSeleccionado != null) {
            Double sumHectareas = vinDAO.sumarHectareasPorUsuario(session, usuarioSeleccionado.getId());
            ventana.getTxtSuma().setText("El usuario tiene un total de " + sumHectareas + " hectáreas.");
        }
        HibernateUtil.commitTx(session);
    } catch (Exception ex) {
        session.getTransaction().rollback();
        Logger.getLogger(VinedoController.class.getName()).log(Level.SEVERE, null, ex);
    }
}


    public static void getAllVinedosByUsuario() {
        List<Vinedo> vinedosusuario = null;
        try {
            HibernateUtil.beginTx(session);
            Usuario usuarioSeleccionado = (Usuario) ventana.getComboUsuario().getSelectedItem();

            if (usuarioSeleccionado != null) {

                vinedosusuario = vinDAO.getAllVinedosByUsuario(session, usuarioSeleccionado.getId());

                modeloTabla = new VinedoTableModel(vinedosusuario);
                ventana.getTabla().setModel(modeloTabla);
            }
            HibernateUtil.commitTx(session);
        } catch (Exception ex) {
            session.getTransaction().rollback();
            Logger.getLogger(NotaController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    public static void limpiarFormulario() {
    ventana.getTxtNombre().setText("");
    ventana.getTxtUbicacion().setText("");
    ventana.getjDateChooser1().setDate(null);
    ventana.getTxtHectareas().setText("");
    ventana.getComboUsuario().setSelectedItem(null);
    
}
    
     public static void limpiarSOLOFormulario() {
    ventana.getTxtNombre().setText("");
    ventana.getTxtUbicacion().setText("");
    ventana.getjDateChooser1().setDate(null);
    ventana.getTxtHectareas().setText("");
    
}
public static void cargarVinedoEnFormulario(Vinedo vinedo) {
    ventana.getComboUsuario().setSelectedItem(vinedo.getUsuario());
    ventana.getTxtNombre().setText(vinedo.getNombre());
    ventana.getTxtUbicacion().setText(vinedo.getUbicacion());
    ventana.getjDateChooser1().setDate(vinedo.getFechaPlantacion());
    ventana.getTxtHectareas().setText(vinedo.getHectareas().toString());
}
// Método para actualizar los datos de los viñedos
public static void actualizarDatosVinedos() {
    cargarComboUsuarios();
  limpiarFormulario();
    modeloTabla.setVinedos(vinDAO.getAllVinedos(session)); 
  
}


}

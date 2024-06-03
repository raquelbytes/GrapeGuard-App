
package controlador;

import Util.TratamientoTableModel;
import Util.VinedoTratamientoTableModel;
import static controlador.VinedoController.vinDAO;
import controlador.factory.HibernateUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import modelo.dao.TratamientoDAO;
import modelo.dao.UsuarioDAO;
import modelo.dao.VinedoDAO;
import modelo.dao.VinedoTratamientoDAO;
import modelo.vo.Tratamiento;
import modelo.vo.Vinedo;
import modelo.vo.VinedoTratamiento;
import org.hibernate.Session;
import vista.MainView;
import vista.TratamientoView;

/**
 *
 * @author raque
 */
public class TratamientoController {
    public static Session session;
    public static TratamientoDAO tatDAO;
    public static VinedoDAO vinDAO;
    public static VinedoTratamientoDAO vintatDAO;
    public static TratamientoView ventana = new TratamientoView();
    public static DefaultComboBoxModel modeloComboVinedo = new DefaultComboBoxModel();
     public static DefaultComboBoxModel modeloComboTratamiento = new DefaultComboBoxModel();
    public static TratamientoTableModel modeloTablaTratamiento;
    public static VinedoTratamientoTableModel modeloTablaVinedoTratamiento;
    
    
    public static void iniciar() {
        ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ventana.setVisible(true);
        ventana.setLocationRelativeTo(null);
        ventana.getComboVinedo().setModel(modeloComboVinedo);
        ventana.getComboTratamiento().setModel(modeloComboTratamiento);
        modeloTablaTratamiento = new TratamientoTableModel(tatDAO.getAllTratamientos(session));
        modeloTablaVinedoTratamiento = new VinedoTratamientoTableModel(new ArrayList <>());
        ventana.getTratamientoTable().setModel(modeloTablaTratamiento);
        ventana.getVinedoTratamientoTable().setModel(modeloTablaVinedoTratamiento);
        
   
    }

    public static void iniciaSession() {
        session = HibernateUtil.getSessionFactory().openSession();
        vintatDAO = HibernateUtil.getVinedoTratamientoDAO();
        tatDAO = HibernateUtil.getTratamientoDAO();
        vinDAO = HibernateUtil.getVinedoDAO();
      
    }

    public static void cerrarSession() {
        session.close();       
    }
    
     public static void cargarComboVinedos() {
        try {
            HibernateUtil.beginTx(session);
            vinDAO.cargarCombo(session, modeloComboVinedo);
            HibernateUtil.commitTx(session);
        } catch (Exception ex) {
            session.getTransaction().rollback();
            Logger.getLogger(TratamientoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
       public static void cargarComboTratamientos() {
        try {
            HibernateUtil.beginTx(session);
            tatDAO.cargarCombo(session, modeloComboTratamiento);
            HibernateUtil.commitTx(session);
        } catch (Exception ex) {
            session.getTransaction().rollback();
            Logger.getLogger(TratamientoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     public static void insertarTratamiento() {
    String nombre = ventana.getTxtNombre().getText();
    String cantidad = ventana.getTxtCantidad().getText();
    String precioUnitario = ventana.getTxtPrecioUni().getText();
    
    if (!nombre.isEmpty() && !cantidad.isEmpty() && !precioUnitario.isEmpty()) {
        try {
            Double precio = Double.valueOf(precioUnitario);
            int cant = Integer.valueOf(cantidad);
            
            HibernateUtil.beginTx(session);
            Tratamiento nuevoTratamiento = new Tratamiento(nombre, cant, precio);
            tatDAO.insertar(session, nuevoTratamiento);
            modeloTablaTratamiento.addTratamiento(nuevoTratamiento);
            HibernateUtil.commitTx(session);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "El formato de cantidad o precio unitario es incorrecto. Asegúrese de ingresar números válidos.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            session.getTransaction().rollback();
            Logger.getLogger(VinedoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    } else {
        JOptionPane.showMessageDialog(null, "Debe introducir todos los campos", "Mensaje", JOptionPane.WARNING_MESSAGE);
    }
}

     
public static void actualizarTratamiento(Tratamiento tratamiento, int filaSeleccionada) {
    try {
        String nombre = ventana.getTxtNombre().getText();
        String cantidad = ventana.getTxtCantidad().getText();
        String precioUnitario = ventana.getTxtPrecioUni().getText();
        
        if (!nombre.isEmpty() && !cantidad.isEmpty() && !precioUnitario.isEmpty()) {
            try {
                Double precio = Double.valueOf(precioUnitario);
                int cant = Integer.valueOf(cantidad);

                HibernateUtil.beginTx(session);
                Tratamiento t = tatDAO.modificar(session, tratamiento, nombre, cantidad, precioUnitario);
                modeloTablaTratamiento.updateTratamiento(filaSeleccionada, t);
                HibernateUtil.commitTx(session);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "El formato de cantidad o precio unitario es incorrecto. Asegúrese de ingresar números válidos.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
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

      
       public static void eliminarTratamiento(Tratamiento tratamiento, int filaSeleccionada) {
        int confirmacion = JOptionPane.showConfirmDialog(
            null,
            "¿Está seguro de que quiere borrar? Borrar un tratamiento supondrá el borrado de este tratamiento de todos los viñedos asociados.",
            "Confirmar borrado",
            JOptionPane.YES_NO_OPTION
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                HibernateUtil.beginTx(session);
                if (tratamiento != null) {
                    tatDAO.borrar(session, tratamiento);
                    modeloTablaTratamiento.removeTratamiento(filaSeleccionada);
                }
                HibernateUtil.commitTx(session);
            } catch (Exception ex) {
                session.getTransaction().rollback();
                Logger.getLogger(TratamientoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            // Salir del método sin borrar nada
            return;
        }
    }  
     public static void insertarTratamientoVinedo(){
          
       Vinedo vin =(Vinedo) ventana.getComboVinedo().getSelectedItem();
       Tratamiento t =(Tratamiento) ventana.getComboTratamiento().getSelectedItem();
        
       if (vin != null && t != null) {
            try { 
                HibernateUtil.beginTx(session);
                VinedoTratamiento nuevoTratamiento = new VinedoTratamiento (vin,t);
                vintatDAO.insertar(session, nuevoTratamiento);
                modeloTablaVinedoTratamiento.addVinedoTratamiento(nuevoTratamiento);
                HibernateUtil.commitTx(session);
            } catch (Exception ex) {
                session.getTransaction().rollback();
                Logger.getLogger(VinedoController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            JOptionPane.showMessageDialog(null, "Debe introducir todos los campos", "Mensaje", JOptionPane.WARNING_MESSAGE);
        }
     
     }
     
      public static void eliminarTratamientoVinedo(VinedoTratamiento vt, int filaSeleccionada) {
        try {
            HibernateUtil.beginTx(session);
            if (vt != null) {
                vintatDAO.borrar(session, vt);
                modeloTablaVinedoTratamiento.removeVinedoTratamiento(filaSeleccionada);
            }
            HibernateUtil.commitTx(session);
        } catch (Exception ex) {
            session.getTransaction().rollback();
            Logger.getLogger(VinedoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
       public static void getAllTratamientobyVinedo() {
          List<VinedoTratamiento> vinedotrat = null;
         try {
            HibernateUtil.beginTx(session);
             Vinedo vin =(Vinedo) ventana.getComboVinedo().getSelectedItem();
         
            if (vin != null) {
            
             vinedotrat = vintatDAO.getTratamientosByVinedo(session,vin.getId());
             

              modeloTablaVinedoTratamiento = new VinedoTratamientoTableModel(vinedotrat);
                ventana.getVinedoTratamientoTable().setModel(modeloTablaVinedoTratamiento);
            }
            HibernateUtil.commitTx(session);
        } catch (Exception ex) {
            session.getTransaction().rollback();
            Logger.getLogger(NotaController.class.getName()).log(Level.SEVERE, null, ex);
        }
  
      
}
}

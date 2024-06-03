/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;

import Util.NotaTableModel;
import com.toedter.calendar.JDateChooser;
import controlador.NotaController;
import static controlador.NotaController.modeloTabla;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import modelo.vo.Nota;
import modelo.vo.Nota.PrioridadNota;
import modelo.vo.Vinedo;

/**
 *
 * @author raque
 */
public class NotaView extends javax.swing.JFrame {
    public static NotaController controlador = new NotaController();
    /**
     * Creates new form MainView
     */
    public NotaView() {
        initComponents();
         controlador.iniciaSession();
         cargarPrioridades();
         
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ComboVinedo = new javax.swing.JComboBox<>();
        PanelFormulario = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtSuma = new javax.swing.JLabel();
        cmbPrioridad = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        taNota = new javax.swing.JTextArea();
        btnAdd = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabla = new javax.swing.JTable();
        btnBorrar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        ComboVinedo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboVinedoActionPerformed(evt);
            }
        });

        jLabel1.setText("Nota");

        jLabel4.setText("Prioridad");

        jLabel5.setText("Cantidad de notas de alta prioridad:");

        taNota.setColumns(20);
        taNota.setRows(5);
        jScrollPane2.setViewportView(taNota);

        btnAdd.setText("Añadir");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanelFormularioLayout = new javax.swing.GroupLayout(PanelFormulario);
        PanelFormulario.setLayout(PanelFormularioLayout);
        PanelFormularioLayout.setHorizontalGroup(
            PanelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelFormularioLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(PanelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(PanelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PanelFormularioLayout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cmbPrioridad, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(51, 51, 51))
            .addGroup(PanelFormularioLayout.createSequentialGroup()
                .addGroup(PanelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelFormularioLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(PanelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtSuma, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)))
                    .addGroup(PanelFormularioLayout.createSequentialGroup()
                        .addGap(114, 114, 114)
                        .addComponent(btnAdd)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PanelFormularioLayout.setVerticalGroup(
            PanelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelFormularioLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(PanelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cmbPrioridad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addComponent(btnAdd)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSuma, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33))
        );

        Tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        Tabla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TablaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(Tabla);

        btnBorrar.setText("Borrar");
        btnBorrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarActionPerformed(evt);
            }
        });

        btnModificar.setText("Modificar");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(PanelFormulario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(123, 123, 123)
                        .addComponent(btnBorrar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnModificar))
                    .addComponent(ComboVinedo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(107, 107, 107)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(ComboVinedo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(PanelFormulario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnBorrar)
                            .addComponent(btnModificar))))
                .addContainerGap(50, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
      
       controlador.cargarComboVinedos();
       controlador.actualizarCount();
      
    }//GEN-LAST:event_formWindowOpened

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
        controlador.cerrarSession();
    }//GEN-LAST:event_formWindowClosed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
        controlador.insertarNota();
        controlador.actualizarCount();
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarActionPerformed
        // TODO add your handling code here:
          int filaSeleccionada = Tabla.getSelectedRow();
    if (filaSeleccionada >= 0) {
        NotaTableModel modelo = (NotaTableModel) Tabla.getModel();
        Nota notaSeleccionada = modelo.getNotaAt(filaSeleccionada);
        controlador.eliminarNota(notaSeleccionada,filaSeleccionada);
        controlador.actualizarCount();
    } else {
        JOptionPane.showMessageDialog(this, "Seleccione una nota para borrar.", "Mensaje", JOptionPane.WARNING_MESSAGE);
    }
    }//GEN-LAST:event_btnBorrarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        // TODO add your handling code here:
                  int filaSeleccionada = Tabla.getSelectedRow();
    if (filaSeleccionada >= 0) {
          NotaTableModel modelo = (NotaTableModel) Tabla.getModel();
        Nota notaSeleccionada = modelo.getNotaAt(filaSeleccionada);
         controlador.actualizarNota(notaSeleccionada,filaSeleccionada);
         controlador.actualizarCount();
        
    } else {
        JOptionPane.showMessageDialog(this, "Seleccione una nota para modificar.", "Mensaje", JOptionPane.WARNING_MESSAGE);
    }
       
    }//GEN-LAST:event_btnModificarActionPerformed

    private void TablaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaMouseClicked
        // TODO add your handling code here:
      int filaSeleccionada = Tabla.rowAtPoint(evt.getPoint());
    
    if (filaSeleccionada >= 0) {
        NotaTableModel modelo = (NotaTableModel) Tabla.getModel();
        Nota notaSeleccionada = modelo.getNotaAt(filaSeleccionada);
        System.out.println("Nota seleccionada: " + notaSeleccionada);
    }
    
    }//GEN-LAST:event_TablaMouseClicked

    private void ComboVinedoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboVinedoActionPerformed
        // TODO add your handling code here:
          controlador.actualizarCount();
    }//GEN-LAST:event_ComboVinedoActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(NotaView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NotaView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NotaView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NotaView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NotaView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<Vinedo> ComboVinedo;
    private javax.swing.JPanel PanelFormulario;
    private javax.swing.JTable Tabla;
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnBorrar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JComboBox<PrioridadNota> cmbPrioridad;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea taNota;
    private javax.swing.JLabel txtSuma;
    // End of variables declaration//GEN-END:variables

    public JComboBox<Vinedo> getComboVinedo() {
        return ComboVinedo;
    }

    public void setComboVinedo(JComboBox<Vinedo> ComboVinedo) {
        this.ComboVinedo = ComboVinedo;
    }


    public JTable getTabla() {
        return Tabla;
    }

    public void setTabla(JTable Tabla) {
        this.Tabla = Tabla;
    }

    public JButton getBtnAdd() {
        return btnAdd;
    }

    public void setBtnAdd(JButton btnAdd) {
        this.btnAdd = btnAdd;
    }

    public JButton getBtnBorrar() {
        return btnBorrar;
    }

    public void setBtnBorrar(JButton btnBorrar) {
        this.btnBorrar = btnBorrar;
    }

    public JButton getBtnModificar() {
        return btnModificar;
    }

    public void setBtnModificar(JButton btnModificar) {
        this.btnModificar = btnModificar;
    }

    public JComboBox<PrioridadNota> getCmbPrioridad() {
        return cmbPrioridad;
    }

    public void setCmbPrioridad(JComboBox<PrioridadNota> cmbPrioridad) {
        this.cmbPrioridad = cmbPrioridad;
    }

    public JTextArea getTaNota() {
        return taNota;
    }

    public void setTaNota(JTextArea taNota) {
        this.taNota = taNota;
    }

    public JLabel getTxtSuma() {
        return txtSuma;
    }

    public void setTxtSuma(JLabel txtSuma) {
        this.txtSuma = txtSuma;
    }
 private void cargarPrioridades() {
        cmbPrioridad.removeAllItems();
        for (Nota.PrioridadNota prioridad : Nota.PrioridadNota.values()) {
            cmbPrioridad.addItem(prioridad);
        }
    }
}

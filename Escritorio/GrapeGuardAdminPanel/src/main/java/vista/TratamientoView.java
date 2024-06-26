/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;

import Util.TratamientoTableModel;
import Util.VinedoTratamientoTableModel;
import com.toedter.calendar.JDateChooser;
import controlador.TratamientoController;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import modelo.vo.Tratamiento;
import modelo.vo.Vinedo;
import modelo.vo.VinedoTratamiento;
import static vista.NotaView.controlador;

/**
 *
 * @author raque
 */
public class TratamientoView extends javax.swing.JFrame {

    public static TratamientoController controlador = new TratamientoController();

    /**
     * Creates new form MainView
     */
    public TratamientoView() {
        initComponents();
        controlador.iniciaSession();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PanelFormulario = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtPrecioTotal = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        txtCantidad = new javax.swing.JTextField();
        btnAdd = new javax.swing.JButton();
        btnBorrar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        txtPrecioUni = new javax.swing.JTextField();
        ComboVinedo = new javax.swing.JComboBox<>();
        comboTratamiento = new javax.swing.JComboBox<>();
        btnAddVinedoTrat = new javax.swing.JButton();
        btnBorrarVinedoTrat = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TratamientoTable = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        VinedoTratamientoTable = new javax.swing.JTable();
        btnActualizar = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Gestión de Tratamientos");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jLabel1.setText("Nombre");

        jLabel2.setText("Cantidad");

        jLabel3.setText("Precio Unitario");

        btnAdd.setText("Añadir");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

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

        ComboVinedo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboVinedoActionPerformed(evt);
            }
        });

        btnAddVinedoTrat.setText("Añadir");
        btnAddVinedoTrat.setToolTipText("");
        btnAddVinedoTrat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddVinedoTratActionPerformed(evt);
            }
        });

        btnBorrarVinedoTrat.setText("Borrar");
        btnBorrarVinedoTrat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarVinedoTratActionPerformed(evt);
            }
        });

        jLabel4.setText("Tratamientos");

        jLabel6.setText("Tratamientos por Viñedo");

        jLabel8.setText("Viñedo:");

        jLabel9.setText("Tratamiento:");

        javax.swing.GroupLayout PanelFormularioLayout = new javax.swing.GroupLayout(PanelFormulario);
        PanelFormulario.setLayout(PanelFormularioLayout);
        PanelFormularioLayout.setHorizontalGroup(
            PanelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelFormularioLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(PanelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelFormularioLayout.createSequentialGroup()
                        .addComponent(btnAdd)
                        .addGap(18, 18, 18)
                        .addComponent(btnModificar)
                        .addGap(18, 18, 18)
                        .addComponent(btnBorrar))
                    .addComponent(ComboVinedo, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(PanelFormularioLayout.createSequentialGroup()
                        .addGroup(PanelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PanelFormularioLayout.createSequentialGroup()
                                .addComponent(btnAddVinedoTrat)
                                .addGap(18, 18, 18)
                                .addComponent(btnBorrarVinedoTrat))
                            .addGroup(PanelFormularioLayout.createSequentialGroup()
                                .addGroup(PanelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(PanelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtPrecioUni, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE)
                                    .addComponent(txtCantidad)
                                    .addComponent(txtNombre)))
                            .addComponent(comboTratamiento, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtPrecioTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PanelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE))
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PanelFormularioLayout.setVerticalGroup(
            PanelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelFormularioLayout.createSequentialGroup()
                .addComponent(jLabel4)
                .addGap(12, 12, 12)
                .addGroup(PanelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(PanelFormularioLayout.createSequentialGroup()
                        .addGroup(PanelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1)
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2))
                    .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(PanelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtPrecioUni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(PanelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdd)
                    .addComponent(btnBorrar)
                    .addComponent(btnModificar))
                .addGap(29, 29, 29)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(ComboVinedo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel9)
                .addGap(3, 3, 3)
                .addComponent(comboTratamiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(PanelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnAddVinedoTrat)
                        .addComponent(btnBorrarVinedoTrat))
                    .addComponent(txtPrecioTotal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16))
        );

        TratamientoTable.setModel(new javax.swing.table.DefaultTableModel(
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
        TratamientoTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TratamientoTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(TratamientoTable);

        VinedoTratamientoTable.setModel(new javax.swing.table.DefaultTableModel(
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
        VinedoTratamientoTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                VinedoTratamientoTableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(VinedoTratamientoTable);

        btnActualizar.setText("Actualizar");
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });

        jLabel5.setText("Tratamientos");

        jLabel7.setText("Tratamientos por Viñedo");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(btnActualizar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(PanelFormulario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(PanelFormulario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnActualizar)
                        .addGap(34, 34, 34))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(59, 59, 59))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ComboVinedoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboVinedoActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_ComboVinedoActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        controlador.cargarComboTratamientos();
        controlador.cargarComboVinedos();
        controlador.getAllTratamientobyVinedo();
        controlador.limpiarFormulario();
    }//GEN-LAST:event_formWindowOpened

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
     
    }//GEN-LAST:event_formWindowClosed

    private void TratamientoTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TratamientoTableMouseClicked
        // TODO add your handling code here:
        int filaSeleccionada = TratamientoTable.rowAtPoint(evt.getPoint());

        if (filaSeleccionada >= 0) {
            TratamientoTableModel modelo = (TratamientoTableModel) TratamientoTable.getModel();
            Tratamiento t = modelo.getTratamientoAt(filaSeleccionada);
            controlador.cargarTratamientoEnFormulario(t);
        }

        
    }//GEN-LAST:event_TratamientoTableMouseClicked

    private void VinedoTratamientoTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_VinedoTratamientoTableMouseClicked
        // TODO add your handling code here:
        int filaSeleccionada = VinedoTratamientoTable.rowAtPoint(evt.getPoint());

        if (filaSeleccionada >= 0) {
            VinedoTratamientoTableModel modelo = (VinedoTratamientoTableModel) VinedoTratamientoTable.getModel();
        }
    }//GEN-LAST:event_VinedoTratamientoTableMouseClicked

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
        controlador.insertarTratamiento();
     
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarActionPerformed
        // TODO add your handling code here:

        int filaSeleccionada = TratamientoTable.getSelectedRow();
        if (filaSeleccionada >= 0) {
            TratamientoTableModel modelo = (TratamientoTableModel) TratamientoTable.getModel();
            Tratamiento tSeleccionado = modelo.getTratamientoAt(filaSeleccionada);
            controlador.eliminarTratamiento(tSeleccionado, filaSeleccionada);
          

        } else {
            JOptionPane.showMessageDialog(this, "Seleccione una tratamiento para borrar.", "Mensaje", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnBorrarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        // TODO add your handling code here:
        int filaSeleccionada = TratamientoTable.getSelectedRow();
        if (filaSeleccionada >= 0) {
            TratamientoTableModel modelo = (TratamientoTableModel) TratamientoTable.getModel();
            Tratamiento tSeleccionado = modelo.getTratamientoAt(filaSeleccionada);
            controlador.actualizarTratamiento(tSeleccionado, filaSeleccionada);
           

        } else {
            JOptionPane.showMessageDialog(this, "Seleccione una tratamiento para modificar.", "Mensaje", JOptionPane.WARNING_MESSAGE);
        }

    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnAddVinedoTratActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddVinedoTratActionPerformed
        // TODO add your handling code here:
        controlador.insertarTratamientoVinedo();
    }//GEN-LAST:event_btnAddVinedoTratActionPerformed

    private void btnBorrarVinedoTratActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarVinedoTratActionPerformed
        // TODO add your handling code here:
        
          int filaSeleccionada = VinedoTratamientoTable.getSelectedRow();
        if (filaSeleccionada >= 0) {
            VinedoTratamientoTableModel modelo = (VinedoTratamientoTableModel) VinedoTratamientoTable.getModel();
            VinedoTratamiento tSeleccionado = modelo.getVinedoTratamientoAt(filaSeleccionada);
            controlador.eliminarTratamientoVinedo(tSeleccionado, filaSeleccionada);

        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un tratamiento para borrar.", "Mensaje", JOptionPane.WARNING_MESSAGE);
        }
        
    }//GEN-LAST:event_btnBorrarVinedoTratActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        // TODO add your handling code here:
        controlador.actualizarDatos();
    }//GEN-LAST:event_btnActualizarActionPerformed

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
            java.util.logging.Logger.getLogger(TratamientoView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TratamientoView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TratamientoView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TratamientoView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TratamientoView().setVisible(true);
            }
        });
    }

    public JComboBox<Vinedo> getComboVinedo() {
        return ComboVinedo;
    }

    public void setComboVinedo(JComboBox<Vinedo> ComboVinedo) {
        this.ComboVinedo = ComboVinedo;
    }

    public JButton getBtnAddVinedoTrat() {
        return btnAddVinedoTrat;
    }

    public void setBtnAddVinedoTrat(JButton btnAddVinedoTrat) {
        this.btnAddVinedoTrat = btnAddVinedoTrat;
    }

    public JButton getBtnBorrarVinedoTrat() {
        return btnBorrarVinedoTrat;
    }

    public void setBtnBorrarVinedoTrat(JButton btnBorrarVinedoTrat) {
        this.btnBorrarVinedoTrat = btnBorrarVinedoTrat;
    }

    public JComboBox<Tratamiento> getComboTratamiento() {
        return comboTratamiento;
    }

    public void setComboTratamiento(JComboBox<Tratamiento> comboTratamiento) {
        this.comboTratamiento = comboTratamiento;
    }

    public JTable getTratamientoTable() {
        return TratamientoTable;
    }

    public void setTratamientoTable(JTable TratamientoTable) {
        this.TratamientoTable = TratamientoTable;
    }

    public JTable getVinedoTratamientoTable() {
        return VinedoTratamientoTable;
    }

    public void setVinedoTratamientoTable(JTable VinedoTratamientoTable) {
        this.VinedoTratamientoTable = VinedoTratamientoTable;
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

    public JTextField getTxtPrecioUni() {
        return txtPrecioUni;
    }

    public void setTxtPrecioUni(JTextField txtPrecioUni) {
        this.txtPrecioUni = txtPrecioUni;
    }

    public JTextField getTxtCantidad() {
        return txtCantidad;
    }

    public void setTxtCantidad(JTextField txtCantidad) {
        this.txtCantidad = txtCantidad;
    }

    public JTextField getTxtNombre() {
        return txtNombre;
    }

    public void setTxtNombre(JTextField txtNombre) {
        this.txtNombre = txtNombre;
    }

    public JLabel getTxtPrecioTotal() {
        return txtPrecioTotal;
    }

    public void setTxtPrecioTotal(JLabel txtPrecioTotal) {
        this.txtPrecioTotal = txtPrecioTotal;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<Vinedo> ComboVinedo;
    private javax.swing.JPanel PanelFormulario;
    private javax.swing.JTable TratamientoTable;
    private javax.swing.JTable VinedoTratamientoTable;
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnAddVinedoTrat;
    private javax.swing.JButton btnBorrar;
    private javax.swing.JButton btnBorrarVinedoTrat;
    private javax.swing.JButton btnModificar;
    private javax.swing.JComboBox<Tratamiento> comboTratamiento;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JLabel txtPrecioTotal;
    private javax.swing.JTextField txtPrecioUni;
    // End of variables declaration//GEN-END:variables
}

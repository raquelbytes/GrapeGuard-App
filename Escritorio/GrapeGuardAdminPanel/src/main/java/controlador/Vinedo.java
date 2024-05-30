/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import controlador.factory.HibernateUtil;
import modelo.dao.UsuarioDAO;
import modelo.dao.VinedoDAO;
import org.hibernate.Session;
import vista.VinedoView;

/**
 *
 * @author raque
 */
public class Vinedo {
    public static Session session;
    public static VinedoDAO vinDAO;
    public static UsuarioDAO usDAO;
    public static VinedoView ventana = new VinedoView();
    
    public static void iniciar() {
        ventana.setVisible(true);
        ventana.setLocationRelativeTo(null);
    }

    public static void iniciaSession() {
        session = HibernateUtil.getSessionFactory().openSession();
        usDAO = HibernateUtil.getUsuarioDAO();
        vinDAO = HibernateUtil.getVinedoDAO();
      
    }

    public static void cerrarSession() {
        session.close();       
    }

 

}

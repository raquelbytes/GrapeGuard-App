/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import controlador.factory.HibernateUtil;
import modelo.dao.TratamientoDAO;
import modelo.dao.UsuarioDAO;
import org.hibernate.Session;
import vista.TratamientoView;

/**
 *
 * @author raque
 */
public class TratamientoController {
    public static Session session;
    public static TratamientoDAO tatDAO;
    public static UsuarioDAO usDAO;
    public static TratamientoView ventana = new TratamientoView();
    
    public static void iniciar() {
        ventana.setVisible(true);
        ventana.setLocationRelativeTo(null);
    }

    public static void iniciaSession() {
        session = HibernateUtil.getSessionFactory().openSession();
        usDAO = HibernateUtil.getUsuarioDAO();
        tatDAO = HibernateUtil.getTratamientoDAO();
      
    }

    public static void cerrarSession() {
        session.close();       
    }
}

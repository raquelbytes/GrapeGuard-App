package controlador;

import controlador.NotaController;
import controlador.TratamientoController;
import controlador.VinedoController;
import javax.swing.JFrame;
import vista.MainView;

public class Main {

    private static final MainView ventana = new MainView();

    public static void main(String[] args) {
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setVisible(true);
        ventana.setLocationRelativeTo(null);
    }

    public static void iniciarNotas() {
        NotaController.iniciar();
    }

    public static void iniciarTratamiento() {
        TratamientoController.iniciar();
    }

    public static void iniciarVinedo() {
        VinedoController.iniciar();
    }

}

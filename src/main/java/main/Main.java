package main;

import conexion.ConexionMYSQL;
import vista.MenuInicial;

/**
 *
 * @author acabrera
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            
            //En este caso se abrira la ventana de configuracion
            java.awt.EventQueue.invokeLater(() -> {
                new MenuInicial().setVisible(true);
            });
        } else if (args[0].equals("True")) {
           
            
        }
    }

}

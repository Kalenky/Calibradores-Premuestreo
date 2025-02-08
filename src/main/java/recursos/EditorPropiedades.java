package recursos;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;
import javax.swing.JOptionPane;
/* @author acabrera 
*/

public class EditorPropiedades {

    private static final String RUTA = "config.properties";
    Properties config = new Properties();
    InputStream configInput = null;

    public EditorPropiedades() {
        buscarPropiedades();

    }

    private void buscarPropiedades() {
        try {
            configInput = new FileInputStream(RUTA);
            config.load(configInput);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error cargando configuración\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public String obtenPropiedad(String dato) {
        if (config.containsKey(dato)) {
            return config.getProperty(dato);
        } else {
            return null;
        }
    }

    public void cambiarPropiedad(String propi, String dato) {
        try {

            config.setProperty(propi, dato);

            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
            config.store(new FileWriter(RUTA), "ACTUALIZADO EL " + format.format(Calendar.getInstance().getTime()));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error guardando la configuración\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}


package conexion;

import java.sql.*;
import javax.swing.JOptionPane;
import recursos.EditorPropiedades;

public class ConexionMYSQL {

    private static final String DRIVERMYSQL = "com.mysql.cj.jdbc.Driver";

    private static Connection cnxM = null;


    public static Connection obtenerNetAgroMayes() {
        EditorPropiedades propiedades = new EditorPropiedades();
        final String usuario = propiedades.obtenPropiedad("MYSQLmayesUsuario");
        final String password = propiedades.obtenPropiedad("MYSQLmayesPassword");
        final String servidor = propiedades.obtenPropiedad("MYSQLmayesServidor");
        final String bbdd = propiedades.obtenPropiedad("MYSQLmayesBBDD");
        final String direccion = "jdbc:mysql://" + servidor + "/" + bbdd;

        if (cnxM == null) {
            try {
                Class.forName(DRIVERMYSQL);
                cnxM = DriverManager.getConnection(direccion, usuario, password);
            } catch (SQLException | ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(null, "Error de conexion a la base de datos " + bbdd + "\nExcepcion " + ex.getMessage() + ex.toString());
            }
        }

        return cnxM;
    }
}

package controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import modelo.Albentrada_lineaspre;

public class PasoDDBB {

    public PasoDDBB(ArrayList<Albentrada_lineaspre> salidaPartida) {
        
        boolean correcto = false;
        try {// Llama al metodo obtenerNetAgroMayes() de la clase ConexionMYSQL   
            Connection con = conexion.ConexionMYSQL.obtenerNetAgroMayes();

            //DEFINICIÓN DE CONSULTAS____________________________________________
            for (Albentrada_lineaspre partida : salidaPartida) {
                int idLineaPre = 0;
                
                //COMPROBACIÓN DE SI EXISTE
                String consulta = "SELECT * FROM albentrada_lineaspre WHERE ALR_idlineaentrada = ? AND ALR_IdCat = ? AND ALR_kilos = ?";
                PreparedStatement psConsulta = con.prepareStatement(consulta);
                psConsulta.setInt(1,partida.getIdLineaEntrada());
                psConsulta.setInt(2, partida.getIdCat());
                psConsulta.setDouble(3, partida.getKilos());
                ResultSet rs = psConsulta.executeQuery();
                 
                if (rs.next()) {
                    idLineaPre = rs.getInt("ALR_id");
                }

                //ACTUALIZAR SI EXISTE LA PARTIDA PERO NO COINCIDE EN ALGUN ATRIBUTO
                if (idLineaPre == 0) {
                    //NO EXISTE

                    String insertar = "INSERT INTO albentrada_lineaspre (ALR_idlineaentrada, ALR_IdCat, ALR_kilos,"
                            + "ALR_Porcentaje, ALR_IdUsuarioLog, ALR_FechaLog, ALR_HoraLog, ALR_Muestreado,"
                            + "ALR_FechaMuestreo) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?)"; // TERMINAR
                    PreparedStatement psInsertar = con.prepareStatement(insertar);

                    // Sustituyo el 1º parámetro de la consulta inicial por la ID de la partida
                    // psConsulta.setInt(1, partida.getId()); 
                    psInsertar.setInt(1, partida.getIdLineaEntrada());
                    psInsertar.setInt(2, partida.getIdCat());
                    psInsertar.setDouble(3, partida.getKilos());
                    psInsertar.setDouble(4, partida.getPorcentaje());
                    psInsertar.setInt(5, partida.getIdUsuarioLog());
                    psInsertar.setDate(6, java.sql.Date.valueOf(partida.getFechaLog()));
                    psInsertar.setTime(7, java.sql.Time.valueOf(partida.getHoraLog()));
                    psInsertar.setDouble(8, partida.getMuestreado());
                    psInsertar.setDate(9, java.sql.Date.valueOf(partida.getFechaMuestreo()));

                    correcto = psInsertar.execute();

                } else {//SI EXISTE

                    String actualizar = "UPDATE albentrada_lineaspre "
                            + "SET ALR_idlineaentrada = ? , ALR_IdCat = ? , ALR_kilos = ? , ALR_Porcentaje = ?"
                            + ",ALR_IdUsuarioLog = ? , ALR_FechaLog = ? , ALR_HoraLog = ? , ALR_Muestreado = ?"
                            + ", ALR_FechaMuestreo = ? WHERE ALR_id = ?";
                    PreparedStatement psActualizar = con.prepareStatement(actualizar);

                    psActualizar.setInt(1, partida.getIdLineaEntrada());
                    psActualizar.setInt(2, partida.getIdCat());
                    psActualizar.setDouble(3, partida.getKilos());
                    psActualizar.setDouble(4, partida.getPorcentaje());
                    psActualizar.setInt(5, partida.getIdUsuarioLog());
                    psActualizar.setDate(6, java.sql.Date.valueOf(partida.getFechaLog()));
                    psActualizar.setTime(7, java.sql.Time.valueOf(partida.getHoraLog()));
                    psActualizar.setDouble(8, partida.getMuestreado());
                    psActualizar.setDate(9, java.sql.Date.valueOf(partida.getFechaMuestreo()));
                    psActualizar.setInt(10, idLineaPre);
                    correcto = psActualizar.execute();

                }
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }

    }

}

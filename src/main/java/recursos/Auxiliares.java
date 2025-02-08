package recursos;

import java.util.HashMap;
import java.util.Map;
import modelo.OrigenDetallePartida;

public class Auxiliares {
    
    // Metodo para asignar calibre basado en los rangos de peso proporcionados como parametro
    public static OrigenDetallePartida asignarCalibre(OrigenDetallePartida linea) {

        //Usar antes de tener la base de datos

        Map<Integer,double[]> mapa = new HashMap<>();

        mapa.put(998, new double[]{25.0,140.0});
        mapa.put(111, new double[]{140.1,170.0});
        mapa.put(111, new double[]{170.1,200.0});
        mapa.put(111, new double[]{200.1,230.0});
        mapa.put(111, new double[]{230.1,265.0});
        mapa.put(111, new double[]{265.1,305.0});
        mapa.put(112, new double[]{305.1,340.0});
        mapa.put(112, new double[]{340.1,390.0});
        mapa.put(128, new double[]{390.1,420.0});
        mapa.put(128, new double[]{420.1,450.0});
        mapa.put(128, new double[]{450.1,550.0});
        mapa.put(998, new double[]{550.1,99999.0});


        int salidaCalibre = 0;
        double maxDistancia = Double.MAX_VALUE;

        for (Map.Entry<Integer, double[]> entry : mapa.entrySet()) {
            double[] rango = entry.getValue();
            double rangoMin = rango[0];
            double rangoMax = rango[1];
            
            double distancia = Math.abs(linea.getPesoMin() - rangoMin) + 
                    Math.abs(linea.getPesoMax() - rangoMax);
            
            if (distancia < maxDistancia) {
                maxDistancia = distancia;
                salidaCalibre = entry.getKey();
            }
        }
        linea.setIdSalidaDDBB(salidaCalibre);
        return linea;
    }
}

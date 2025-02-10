package controlador;

import java.io.BufferedReader;
import java.io.File;

import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import modelo.Albentrada_lineaspre;

import modelo.OrigenDetallePartida;
import modelo.OrigenPartida;
import recursos.Auxiliares;
import recursos.EditorPropiedades;

public class TratarArchivos {

    DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    public TratarArchivos() {

        EditorPropiedades e = new EditorPropiedades();

        String ruta = e.obtenPropiedad("RutaCarpeta");

        File carpeta = new File(ruta);

        // FilenameFilter filtor2 = (dir, name) -> name.toLowerCase().endsWith(".csv");
        FilenameFilter filtor = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".csv");
            }
        };

        File[] archivos = carpeta.listFiles(filtor);

        //RECORRE CADA ARCHIVO
        for (int i = 0; i < archivos.length; i++) {
            ProcesarArchivo(archivos[i]);
        }
    }

    private void ProcesarArchivo(File archivo) {

        ArrayList<OrigenDetallePartida> detallePartida = new ArrayList<OrigenDetallePartida>();
        OrigenPartida salidaPartida = null;
        String rutaArchivo="";
        try {
            //ABRE EL .CVS
            FileReader cvs = new FileReader(archivo.getAbsoluteFile());
            BufferedReader br = new BufferedReader(cvs);
            //LEE CADA LINEA DEL .CVS
            int k = 1; //variable para saber en que linea del documento estamos
            String salida;
            while ((salida = br.readLine()) != null) {

                if (k == 1) {
                   rutaArchivo=archivo.getAbsolutePath();
                } else if (k == 2) {

                    salidaPartida = ObtenerPartida(salida);

                } else {

                    OrigenDetallePartida salidaDetallePartida = ObtenerDetallePartida(salida);

                    detallePartida.add(Auxiliares.asignarCalibre(salidaDetallePartida));

                }
                k++;
            }
            salidaPartida.setDetalle(detallePartida);

            processarAntesDeEnviarDDBB(salidaPartida,rutaArchivo);

        } catch (IOException ex) {
            System.err.println("Error leyendo el archivo: " + archivo.getName());
            ex.printStackTrace();
        }

    }

    private void processarAntesDeEnviarDDBB(OrigenPartida salidaPartida,String rutaArchivo) {

        //OBTNERER DE LA BASE DE DATOS EL NUMERO INTERNO DE LA PARTIDA.
        int obtenerIDPartida = obtenerPartida(salidaPartida.getNumeroPartida());

        //PROCESAR ORIGEN PARTIDA Y CONVERLO EN ALNBENTRADA_LINEASPRE
        ArrayList<Albentrada_lineaspre> enviarBBDD = new ArrayList<>();

        ArrayList<OrigenDetallePartida> origenDatosDetalle = salidaPartida.getDetalle();

        for (int i = 0; i < origenDatosDetalle.size(); i++) {
            OrigenDetallePartida lineaDetalle = origenDatosDetalle.get(i);

            Albentrada_lineaspre lineaEnvio = new Albentrada_lineaspre(0, obtenerIDPartida, lineaDetalle.getIdSalidaDDBB(), lineaDetalle.getPesoTotal(), 0.0, -1, LocalDate.now(), LocalTime.now(), lineaDetalle.getPesoTotal(), LocalDate.from(salidaPartida.getfechaFin()));

            enviarBBDD.add(lineaEnvio);

            PasoDDBB enviarDatos = new PasoDDBB(enviarBBDD,rutaArchivo);

        }

    }

    private OrigenDetallePartida ObtenerDetallePartida(String readLine) {
        String[] elementos = readLine.split(";");

        String grupo = elementos[12];
        double pesoMin = Double.parseDouble(elementos[12].replaceAll(",", "."));
        double pesoMax = Double.parseDouble(elementos[13].replaceAll(",", "."));
        double pesoTotal = Double.parseDouble(elementos[14].replaceAll(",", "."));
        int udTotal = Integer.parseInt(elementos[15]);
        int envasesTotal = Integer.parseInt(elementos[16]);

        OrigenDetallePartida origenDetallePartida = new OrigenDetallePartida(grupo, pesoMin, pesoMax, pesoTotal, udTotal, envasesTotal);

        return Auxiliares.asignarCalibre(origenDetallePartida);
    }

    private OrigenPartida ObtenerPartida(String readLine) {

        String[] elementos = readLine.split(";");

        int numeroPartida = Integer.parseInt(elementos[0]);
        String descripcion = elementos[1];
        String programa = elementos[2];
        String productor = elementos[3];

        LocalDateTime fechaInicio = LocalDateTime.parse(elementos[4], formato);
        LocalDateTime fechaFin = LocalDateTime.parse(elementos[5], formato);

        double pesoInicial = Double.parseDouble(elementos[6].replaceAll(",", "."));
        double pesoTotal = Double.parseDouble(elementos[7].replaceAll(",", "."));
        double pesoNeto = Double.parseDouble(elementos[8].replaceAll(",", "."));
        double pesoCalibrado = Double.parseDouble(elementos[9].replaceAll(",", "."));
        int unidades = Integer.parseInt(elementos[10]);

        OrigenPartida origenPartida = new OrigenPartida(numeroPartida, descripcion, programa, productor, fechaInicio, fechaFin, pesoInicial,
                pesoTotal, pesoNeto, pesoCalibrado, unidades);
        return origenPartida;
    }

    // Terminarrrrr
    private Albentrada_lineaspre TratarPartida(OrigenPartida origenPartida) {

        origenPartida.getDetalle();

        return null;
    }

    private int obtenerPartida(int numeroPartida) {
        
        Connection con = conexion.ConexionMYSQL.obtenerNetAgroMayes();
        
        int campana = 25;
        
        try {

            String sql = "SELECT ael.AEL_idlinea FROM albentrada_lineas ael\n"
                    + "INNER JOIN albentrada ae ON ael.AEL_idalbaran=ae.AEN_idalbaran\n"
                    + "WHERE ae.AEN_campa=? AND ael.AEL_muestreo=?";
            
            PreparedStatement ps = con.prepareStatement(sql);
            
            ps.setInt(1, campana);
            ps.setInt(2, numeroPartida);
            
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()){
                return rs.getInt(1);
            }else{
                return 0;
            }
            
            

        } catch (SQLException e) {
            System.out.println(e.toString());
            return 0;
        }
    }
    
    
    
    
    //Recibe un ruta de un archivo  devuelve true si lo lgra.
    public static boolean moverArchivo(String rutaOrigen){
        EditorPropiedades e = new   EditorPropiedades();
        String rutaDestino = e.obtenPropiedad("rutaDestino");
       
        
        
        return true;
    }
    
    
    public static DefaultTableModel listarArchivos (String ruta,DefaultTableModel modeloOriginal){
        
        
    /*
        
            public TratarArchivos() {

        EditorPropiedades e = new EditorPropiedades();

        String ruta = e.obtenPropiedad("RutaCarpeta");

        File carpeta = new File(ruta);

        
        FilenameFilter filtor = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".csv");
            }
        };

        File[] archivos = carpeta.listFiles(filtor);

        
        for (int i = 0; i < archivos.length; i++) {
            ProcesarArchivo(archivos[i]);
        }
    
        
        private DefaultTableModel obtenerPartida(String partida,DefaultTableModel modelo){
     int idPartida;
        try{
            idPartida=Integer.parseInt(partida);
            
            Connection con= conexion.ConexionMYSQL.obtenerNetAgroMayes();
         
            PreparedStatement ps = con.prepareStatement("SELECT * FROM albentrada_lineaspre WHERE ALR_idlineaentrada=?");
            ps.setInt(1, idPartida);
            
            ResultSet rs = ps.executeQuery();
            
            ResultSetMetaData rsmd = ps.getMetaData();
            
           
            
            for(int i=1;i<=rsmd.getColumnCount();i++){
                modelo.addColumn(rsmd.getColumnName(i));
            }
            
            while(rs.next()){
                
                Object[]linea=new Object[rsmd.getColumnCount()];
                 for(int i=0;i<rsmd.getColumnCount();i++){
                    linea[i]=rs.getObject(i+1);
                 }
                 modelo.addRow(linea);
            }
            
         
            
        }catch(NumberFormatException|SQLException  e){
            System.out.println(e.toString());
            JOptionPane.showMessageDialog(this,"No es una partida correcta");
        }
        */
      
        modeloOriginal.addRow(new Object[]{"P0000001","12-01-2025"});
    
    return modeloOriginal;
    
    }
    
    
    
}

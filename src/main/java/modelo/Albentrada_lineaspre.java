package modelo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

//Para obtener el ALR_idlineaentrada tienes que obtenerlo mediante una consulta a 
// albentrada_lineas el campo muestreo.

public class Albentrada_lineaspre {
    // Variables privadas
    private int id;
    private int idLineaEntrada;
    private int idCat;
    private double kilos;
    private double porcentaje;
    private int idUsuarioLog;
    private LocalDate fechaLog;
    private LocalTime horaLog;
    private double muestreado;
    private LocalDate fechaMuestreo;

    // Constructor
    public Albentrada_lineaspre(int id, int idLineaEntrada, int idCat, double kilos, double porcentaje, 
                   int idUsuarioLog, LocalDate fechaLog, LocalTime horaLog, 
                   double muestreado, LocalDate fechaMuestreo) {
        this.id = id;
        this.idLineaEntrada = idLineaEntrada;
        this.idCat = idCat;
        this.kilos = kilos;
        this.porcentaje = porcentaje;
        this.idUsuarioLog = idUsuarioLog;
        this.fechaLog = fechaLog;
        this.horaLog = horaLog;
        this.muestreado = muestreado;
        this.fechaMuestreo = fechaMuestreo;
    }

   

    // Getters
    public int getId() {
        return id;
    }

    public int getIdLineaEntrada() {
        return idLineaEntrada;
    }

    public int getIdCat() {
        return idCat;
    }

    public double getKilos() {
        return kilos;
    }

    public double getPorcentaje() {
        return porcentaje;
    }

    public int getIdUsuarioLog() {
        return idUsuarioLog;
    }

    public LocalDate getFechaLog() {
        return fechaLog;
    }

    public LocalTime getHoraLog() {
        return horaLog;
    }

    public double getMuestreado() {
        return muestreado;
    }

    public LocalDate getFechaMuestreo() {
        return fechaMuestreo;
    }

    @Override
    public String toString() {
        return "Destino_LineasPremuestreo{" + "id=" + id + ", idLineaEntrada=" + 
                idLineaEntrada + ", idCat=" + idCat + ", kilos=" + kilos + 
                ", porcentaje=" + porcentaje + ", idUsuarioLog=" + idUsuarioLog + 
                ", fechaLog=" + fechaLog + ", horaLog=" + horaLog + ", muestreado=" 
                + muestreado + ", fechaMuestreo=" + fechaMuestreo + '}';
    }
   
}

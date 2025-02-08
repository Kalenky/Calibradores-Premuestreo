package modelo;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class OrigenPartida {
    
    private int numeroPartida;
    private String descripcion;
    private String programa;
    private String productor;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private double pesoInicial;
    private double pesoTotal;
    private double pesoNeto;
    private double pesoCalibrado;
    private int unidades;
    ArrayList<OrigenDetallePartida> detalle;
    
    public OrigenPartida(int numeroPartida, String descripcion, String programa, String productor,
                   LocalDateTime fechaInicio, LocalDateTime fechaFin, double pesoInicial, double pesoTotal,
                   double pesoNeto, double pesoCalibrado, int unidades ) {
        this.numeroPartida = numeroPartida;
        this.descripcion = descripcion;
        this.programa = programa;
        this.productor = productor;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.pesoInicial = pesoInicial;
        this.pesoTotal = pesoTotal;
        this.pesoNeto = pesoNeto;
        this.pesoCalibrado = pesoCalibrado;
        this.unidades = unidades;
       
    }

    public void setDetalle(ArrayList<OrigenDetallePartida> detalle) { 
        this.detalle = (ArrayList<OrigenDetallePartida>) detalle.clone();
    }
    
    
    // Getters 
    public int getNumeroPartida() {
        return numeroPartida;
    }
    
    public String getDescripcion(){
        return descripcion;
    }
    
    public String getPrograma(){
        return programa;
    }
    
    public String getProductor(){
        return productor;
    }
    
    public LocalDateTime getfechaInicio(){
        return fechaInicio;
    }
    
    public LocalDateTime getfechaFin(){
        return fechaFin;
    }
    
    public double getPesoInicial(){
        return pesoInicial;
    }
    
    public double getPesoTotal(){
        return pesoTotal;
    }
    
    public double getPesoNeto(){
        return pesoNeto;
    }
    
    public double getPesoCalibrado(){
        return pesoCalibrado;
    }
    
    public int unidades(){
        return unidades;
    }

    public ArrayList<OrigenDetallePartida> getDetalle() {
        return detalle;
    }

    @Override
    public String toString() {
        return "OrigenPartida{" + "numeroPartida=" + numeroPartida + ", descripcion=" 
                + descripcion + ", programa=" + programa + ", productor=" + productor + 
                ", fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin + ", pesoInicial=" 
                + pesoInicial + ", pesoTotal=" + pesoTotal + ", pesoNeto=" + pesoNeto + ", pesoCalibrado=" 
                + pesoCalibrado + ", unidades=" + unidades + ", "
                + "detalle=" + detalle.toString()+ " '}'";
    }
  
}

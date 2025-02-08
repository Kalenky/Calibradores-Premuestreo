package modelo;
/*@author acabrera
 */

public class OrigenDetallePartida {
    
    private String grupo;
    private double pesoMin;
    private double pesoMax;
    private double pesoTotal;
    private int UdTotal;
    private int envasesTotal;
    private int idSalidaDDBB;
    
    public OrigenDetallePartida(String grupo, double pesoMin, double pesoMax, 
            double pesoTotal, int UdTotal, int envasesTotal){ 
        
       this.grupo = grupo;
       this.pesoMin = pesoMin;
       this.pesoMax = pesoMax;
       this.pesoTotal = pesoTotal;
       this.UdTotal = UdTotal;
       this.envasesTotal = envasesTotal;
    }

   

    public String getGrupo() {
        return grupo;
    }

    public double getPesoMin() {
        return pesoMin;
    }

    public double getPesoMax() {
        return pesoMax;
    }

    public double getPesoTotal() {
        return pesoTotal;
    }

    public int getUdTotal() {
        return UdTotal;
    }
    
    public int getEnvasesTotal() {
        return envasesTotal;
    }

    public int getIdSalidaDDBB() {
        return idSalidaDDBB;
    }

    public void setIdSalidaDDBB(int idSalidaDDBB) {
        this.idSalidaDDBB = idSalidaDDBB;
    }

    
    @Override
    public String toString() {
        return "\nOrigenDetallePartida{" + "grupo=" + grupo + ", pesoMin=" + pesoMin + 
                ", pesoMax=" + pesoMax + ", pesoTotal=" + pesoTotal + ", UdTotal=" + UdTotal 
                + ", envasesTotal=" + envasesTotal + ", IdSalidaDDBB=" + idSalidaDDBB +'}';
    }
    
    
    
    
    
    
            
    
}

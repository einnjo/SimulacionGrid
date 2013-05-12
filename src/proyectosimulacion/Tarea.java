package proyectosimulacion;

/**
 *
 * @author Juan Jose Leñero
 */
public class Tarea {
    private int id;
    private Double tiempoLlegada;
    private Double runTime;
    private int reqProcs;
    private Double tiempoSalida;

    public Tarea(int id, Double tiempoLlegada, Double runTime, int reqProcs) {
        this.id = id;
        this.tiempoLlegada = tiempoLlegada;
        this.runTime = runTime;
        this.reqProcs = reqProcs;
        tiempoSalida=0.0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getTiempoLlegada() {
        return tiempoLlegada;
    }

    public void setTiempoLlegada(Double tiempoLlegada) {
        this.tiempoLlegada = tiempoLlegada;
    }

    public Double getRunTime() {
        return runTime;
    }

    public void setRunTime(Double runTime) {
        this.runTime = runTime;
    }

    public int getReqProcs() {
        return reqProcs;
    }

    public void setReqProcs(int reqProcs) {
        this.reqProcs = reqProcs;
    }

    public Double getTiempoSalida() {
        return tiempoSalida;
    }

    public void setTiempoSalida(Double tiempoSalida) {
        this.tiempoSalida = tiempoSalida;
    }

    
    
    
    
}

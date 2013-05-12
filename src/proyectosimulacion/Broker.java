package proyectosimulacion;

import java.util.ArrayList;

/**
 *
 * @author Juan Jose Leñero
 */
public abstract class Broker {
    protected int nProcs;
    protected ArrayList<Integer> procsAsignados;
    protected Procesador[] procesadores;
    public String tipo;
    protected Procesador procAsignado;
    
    public  abstract ArrayList<Integer> seleccionaProcs(int reqProcs, Procesador[] procesadores);
    
    public Broker(int nProcs){
        this.nProcs = nProcs;
        procesadores = new Procesador[nProcs];
        procsAsignados = new ArrayList<>(nProcs);
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    
    
    
}

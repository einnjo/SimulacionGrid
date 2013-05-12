package proyectosimulacion;

/**
 *
 * @author Juan Jose Leñero
 */
public class ParProcesadorCola implements Comparable<ParProcesadorCola>{
    private Integer procesador;
    private Integer cola;

    public ParProcesadorCola(int procesador, int cola) {
        this.procesador = procesador;
        this.cola = cola;
    }
    
    @Override
    public int compareTo(ParProcesadorCola p) {
        if(this.cola<p.getCola()){
            return -1;
        }
        if(this.cola == p.getCola()){
            return 0;
        }
        return 1;
    }

    public int getProcesador() {
        return procesador;
    }

    public void setProcesador(int procesador) {
        this.procesador = procesador;
    }

    public int getCola() {
        return cola;
    }

    public void setCola(int cola) {
        this.cola = cola;
    }

   
    
}

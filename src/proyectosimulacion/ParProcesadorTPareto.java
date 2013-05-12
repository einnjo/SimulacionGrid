package proyectosimulacion;

/**
 *
 * @author Juan Jose Leñero
 */
public class ParProcesadorTPareto implements Comparable<ParProcesadorTPareto> {
    private Integer procesador;
    private Double tPareto;

    public ParProcesadorTPareto(int procesador, Double tPareto) {
        this.procesador = procesador;
        this.tPareto = tPareto;
    }
    
    @Override
    public int compareTo(ParProcesadorTPareto p) {
        if(this.tPareto<p.gettPareto()){
            return -1;
        }
        if(this.tPareto == p.gettPareto()){
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

    public Double gettPareto() {
        return tPareto;
    }

    public void settPareto(Double tPareto) {
        this.tPareto = tPareto;
    }
    
    
}

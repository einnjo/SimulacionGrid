package proyectosimulacion;

import java.util.ArrayList;

/**
 *
 * @author Juan Jose Leñero
 */
public class BrokerRoundRobin extends Broker{

    private int indice = 0;
    
    public BrokerRoundRobin(int nProcs) {
        super(nProcs);
        tipo = "RoundRobin";
    }
    
    
    @Override
    public ArrayList<Integer> seleccionaProcs(int reqProcs, Procesador[] procesadores) {
        // limpia la lista de datos previos
        procsAsignados.clear();
        for(int i=0;i<reqProcs;i++){
            procsAsignados.add(indice);
            indice = (indice + 1)%nProcs;
        }
        return procsAsignados;
    }

}

package proyectosimulacion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 *
 * @author Juan Jose Leñero
 */
public class BrokerList extends Broker{
    public BrokerList(int nProcs) {
        super(nProcs);
        tipo = "List";
    }
    @Override
    public ArrayList<Integer> seleccionaProcs(int reqProcs, Procesador[] procesadores) {
        // limpia la lista de datos previos
        procsAsignados.clear();
        ArrayList<ParProcesadorCola> pares = new ArrayList<>();
        for(int i=0; i<procesadores.length;i++){
            pares.add(new ParProcesadorCola(i, procesadores[i].getTamanoCola()));
        }
        Collections.sort(pares);
        
        for(int i=0; i<reqProcs; i++){
            procsAsignados.add(pares.get(0).getProcesador());
        }
        
        return procsAsignados;
    }

}

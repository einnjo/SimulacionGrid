package proyectosimulacion;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Juan Jose Leñero
 */
public class BrokerParetoFractal extends Broker{
    public static final Double MU = 0.00034229;
    public static final Double H = 0.8;
    public static final Double ALPHA = 1.58;
    public static final Double A = 600.0;
    public static final Double PHI = 0.05;
    public BrokerParetoFractal(int nProcs) {
        super(nProcs);
        tipo = "ParetoFractal";
    }
    
    @Override
    public ArrayList<Integer> seleccionaProcs(int reqProcs, Procesador[] procesadores) {
        Double tF;
        Double tP;
        int n;
        Procesador proc;
        // limpia la lista de datos previos
        procsAsignados.clear();
        ArrayList<ParProcesadorTPareto> pares = new ArrayList<>();
        for(int i=0; i<procesadores.length;i++){
            proc = procesadores[i];
            n = proc.getNumTareas();
            tF = (n*MU)+((A*Math.pow(n, H))/(Math.pow(PHI, 1/ALPHA)));
            tP = proc.gettEjecUTarea()*(Math.pow(PHI, -1*(1/ALPHA))-1);
            pares.add(new ParProcesadorTPareto(i, tF+tP));
        }
        Collections.sort(pares);
        
        for(int i=0; i<reqProcs; i++){
            procsAsignados.add(pares.get(0).getProcesador());
        }
        
        return procsAsignados;
    }

}

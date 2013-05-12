package proyectosimulacion;

import java.util.ArrayList;

/**
 *
 * @author Juan Jose Leñero
 */
public class Simulador {
    public static final Double LAMBDA = 0.027383;
    
    // numero de tareas procesadas
    private int tareasProcesadas;
    // reloj principal del sistema
    public static Double reloj;
    // lista de eventos futuros
    private ArrayList<Evento> eventos;
    // lista de tareas que necesitan calcular salida
    private ArrayList<Tarea> tareasPorGenerarSalida;
    // numero de procesadores simulados
    private int nProcs;
    // arreglo de los procesadores
    private Procesador[] procesadores;;
    // broker
    private Broker broker; 
    
    //ESTADISTICAS   
    // tamano promedio de cola por procesador
    Double colaPromedio;
    // tamano de cola maximo
    Double colaMax;
    // tiempo promedio en el sistema
    Double tSistemaPromedio;
    // makespan, maximo tiempo en el sistema
    Double makespan;
    
    
    
    public Simulador(int nProcs, ArrayList<Evento> eventos, Broker broker){
        this.nProcs = nProcs;
        this.eventos = eventos;
        this.broker = broker;
        reloj = 0.0;
        procesadores = new Procesador[nProcs];
        for(int i=0; i<procesadores.length;i++){
            procesadores[i] = new Procesador();
        }
        tareasProcesadas = 0;
        tareasPorGenerarSalida = new ArrayList<>();
        colaMax = 0.0;
        colaPromedio = 0.0;
        tSistemaPromedio = 0.0;
        makespan = 0.0;
    }
    
    public void simula(){
        // mientras existan eventos por simular
        while(!eventos.isEmpty()){
            avanzaSimulacion();
        }
        //System.out.println("\nFINALIZADA T:"+reloj);
        calculaEstadisticas();
        despliegaEstadisticas();
    }
    
    public void avanzaSimulacion(){
        Double tInminente = eventos.get(eventos.size()-1).getTiempo();
        Evento evento;
        do{
        // remueve el evento inminente (El siguiente en ocurrir)
        evento = eventos.remove(eventos.size()-1);
        // obten el tiempo del evento inminente
        tInminente = evento.getTiempo(); 
        // avanza el reloj de la simulacion al tiempo inminente
        reloj = tInminente;
        // procesa el evento inminente
        procesaEvento(evento.getTipo(), evento.getTarea());
        }while(!eventos.isEmpty() && tInminente == eventos.get(eventos.size()-1).getTiempo());
        
        // genera nuevos eventos de salida
        if(!tareasPorGenerarSalida.isEmpty()){
            for(int i=0; i<tareasPorGenerarSalida.size();i++){
                generaEventoSalida(tareasPorGenerarSalida.get(i));
            }
            tareasPorGenerarSalida.clear();
        }
        // guarda datos estadisticos
         actualizaEstadisticasEnProcesadores();
    }
    
    public void procesaEvento(int tipo, Tarea tarea){
        switch(tipo){
            case Evento.TIPO_LLEGADA:
                procesaLlegada(tarea);
                break;
            case Evento.TIPO_SALIDA:
                procesaSalida(tarea);
                break;
        }
    }
    
    public void procesaLlegada(Tarea tarea){
        // si la tarea requiere mas procesadores de los que hay disponibles
        if(tarea.getReqProcs() > nProcs){
            return; // se descarta la tarea
        }
        // pide al broker los procesadores para la tarea
        ArrayList<Integer> procsAsignados = broker.seleccionaProcs(tarea.getReqProcs(), procesadores);
        // determina el tiempo en el que la tarea se ejecutara simultaneamente en todos los procesadores
        Double tEjecucion = getTiempoEjecucionSimultanea(tarea.getTiempoLlegada(),procsAsignados);
        // calcula el tiempo de salida a partir del de ejecucion
        tarea.setTiempoSalida(tEjecucion+tarea.getRunTime());
        // manda la tarea a los procesadores asignados
        asignaTarea(tarea, procsAsignados);
        // toda tarea que entra al sistema debe salir
        tareasPorGenerarSalida.add(tarea);
        
    }
    
    public void asignaTarea(Tarea tarea, ArrayList<Integer> procsAsignados){
        for(int i=0; i<procsAsignados.size();i++){
            procesadores[procsAsignados.get(i)].aceptaTarea(tarea);
        }
    }
    
    public void procesaSalida(Tarea tarea){
        Procesador proc;
        for(int i=0; i<procesadores.length;i++){
            proc = procesadores[i];
            // si la tarea a despachar esta siendo procesada en el cpu del procesador
            if(proc.getEstado() == Procesador.Estado_Ocupado && proc.getTareaEnCpu().getId() == tarea.getId()){
                proc.despachaTarea();
            }
        }
       tareasProcesadas++;
       //System.out.print("\r"+tareasProcesadas+"/65535");
    }
    
    public Double getTiempoEjecucionSimultanea(Double tiempoLlegada, ArrayList<Integer>procsAsignados){
        // tiempo de ejecucion ideal, todos los procesadores requeridos estan libres
        Double tiempoEjecucionSimultanea = tiempoLlegada;
        Procesador procAsignado;
        for(int i=0; i<procsAsignados.size();i++){
            procAsignado = procesadores[procsAsignados.get(i)];
            // si el procesador revisado esta libre
            if(procAsignado.getEstado() == Procesador.Estado_Espera){
                // no hay cambio en el tiempo de ejecucion
                continue; 
            }
            // si la cola del procesador revisado esta libre pero no su cpu
            if (procAsignado.getColaTareas().isEmpty()){
                // es el maximo entre el tiempo actual y el tiempo de salida de la unica tarea en el procesador
                tiempoEjecucionSimultanea = Math.max(tiempoEjecucionSimultanea,procAsignado.getTareaEnCpu().getTiempoSalida());
                continue;
            }
            // si el procesador revisado tiene cola, es el maximo entre el tiempo actual y el tiempo de salida de la ultima tarea en la cola
            tiempoEjecucionSimultanea = Math.max(tiempoEjecucionSimultanea, procAsignado.getColaTareas().get(0).getTiempoSalida());
        }
        return tiempoEjecucionSimultanea;
    }
    
    public void generaEventoSalida(Tarea tarea){ 
        Evento evento = new Evento(Evento.TIPO_SALIDA, tarea.getTiempoSalida(), tarea);
        Evento.InsertaEnOrden(evento, eventos);
    }
    
    public void actualizaEstadisticasEnProcesadores(){
        for(int i=0; i<procesadores.length;i++){
            procesadores[i].actualizaEstadisticas();
        }
    }
    
    public void calculaEstadisticas(){
        Procesador p;
        Double colaAcum = 0.0;
        Double tiempoSistemaAcum =0.0;
        for(int i=0; i<procesadores.length;i++){
            p = procesadores[i];
            colaMax = Math.max(colaMax, p.getMaxCola());
            colaAcum += p.getColaProm();
            tiempoSistemaAcum += p.gettSistema();
            makespan = Math.max(makespan, p.gettSistema());
        }
        colaPromedio = colaAcum/nProcs;
        tSistemaPromedio = tiempoSistemaAcum/tareasProcesadas;
    }
    
    public void despliegaEstadisticas(){
        System.out.printf("%20d%20s%20.0f%20.0f%20.0f%20.0f%20.0f%n", nProcs,broker.getTipo(),reloj,tSistemaPromedio,makespan,colaPromedio,colaMax);
    }
}

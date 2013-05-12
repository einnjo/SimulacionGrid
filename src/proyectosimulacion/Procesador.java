package proyectosimulacion;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author Juan Jose Leñero
 */
public class Procesador {
    public static final int Estado_Espera = 0;
    public static final int Estado_Ocupado = 1;
    
    // identificador unico
    private static AtomicInteger siguienteId = new AtomicInteger();
    private final int id;
  
    private ArrayList<Tarea> colaTareas;
    private Tarea tareaEnCpu;
    private int estado;
   
    // numero de tareas en el procesador
    private int numTareas;
    
    // tiempo de ejecucion de la ultima tarea
    private Double tEjecUTarea;
    
    // ESTADISTICAS
    private int maxCola;
    private Double tActual;
    private Double tAnterior;
    private Double colaAcum;
    private int colaAnterior;
    private Double colaProm;
    private Double tServicio;
    private Double tCola;
    private int edoAnterior;
    private Double tSistema;
    
    public Procesador(){
        id = siguienteId.incrementAndGet();
        estado = 0;
        numTareas=0;
        colaTareas = new ArrayList<>(50);
        tEjecUTarea = 0.0;
        // ESTADISTICAS
        maxCola = 0;
        tActual =0.0;
        tAnterior = 0.0;
        colaAcum = 0.0;
        colaAnterior = 0;
        colaProm = 0.0;
        tServicio = 0.0;
        tCola = 0.0;
        edoAnterior = Estado_Espera;
        tSistema = 0.0;
    }
    
    public void aceptaTarea(Tarea tarea){
        numTareas++;
        if(estado == Estado_Espera){
            tEjecUTarea = Simulador.reloj;
            // no hay cola pasa a cpu
            tareaEnCpu = tarea;
            // actualizar estado
            estado = Estado_Ocupado;
        }else{
            // se forma en la cola
            colaTareas.add(0, tarea);
        }
    }
    
    public void despachaTarea(){
        numTareas--;
        if(colaTareas.isEmpty()){
            estado = Estado_Espera;
            tareaEnCpu= null;
        }else{
            tareaEnCpu = colaTareas.remove(colaTareas.size()-1);
            estado = Estado_Ocupado;
            tEjecUTarea = Simulador.reloj;
        }
        
    }
    
    public void actualizaEstadisticas(){
        tActual = Simulador.reloj;
        actualizaMaxTamanoCola();
        actualizaColaAcum();
        actualizaColaPromedio();
        actualizaTiempoServicio();
        actualizaTiempoCola();
        actualizaTiempoEnSistema();
        tAnterior = tActual;
        colaAnterior = getTamanoCola();
    }
    
    private void actualizaMaxTamanoCola(){
        maxCola = Math.max(maxCola, getTamanoCola());
    }
    
    private void actualizaColaAcum(){
        colaAcum += colaAnterior*(tActual-tAnterior);
    }
    
    private void actualizaColaPromedio(){
        colaProm =  (tActual != 0) ? colaAcum/tActual : 0;
    }
    
    private void actualizaTiempoServicio(){
        tServicio += (edoAnterior == Estado_Ocupado) ? (tActual-tAnterior): 0;
    }
    
    private void actualizaTiempoCola(){
        tCola += (colaAnterior > 0) ? (tActual-tAnterior): 0;
    }
    
    private void actualizaTiempoEnSistema(){
        tSistema = tCola + tServicio;
    }
    
    public int getTamanoCola(){
        return colaTareas.size();
    }

    public ArrayList<Tarea> getColaTareas() {
        return colaTareas;
    }

    public void setColaTareas(ArrayList<Tarea> colaTareas) {
        this.colaTareas = colaTareas;
    }

    public Tarea getTareaEnCpu() {
        return tareaEnCpu;
    }

    public void setTareaEnCpu(Tarea tareaEnCpu) {
        this.tareaEnCpu = tareaEnCpu;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getNumTareas() {
        return numTareas;
    }

    public void setNumTareas(int numTareas) {
        this.numTareas = numTareas;
    }

    public Double gettEjecUTarea() {
        return tEjecUTarea;
    }

    public void settEjecUTarea(Double tEjecUTarea) {
        this.tEjecUTarea = tEjecUTarea;
    }

    public int getMaxCola() {
        return maxCola;
    }

    public void setMaxCola(int maxCola) {
        this.maxCola = maxCola;
    }

    public Double getColaProm() {
        return colaProm;
    }

    public void setColaProm(Double colaProm) {
        this.colaProm = colaProm;
    }

    public Double gettSistema() {
        return tSistema;
    }

    public void settSistema(Double tSistema) {
        this.tSistema = tSistema;
    }
    
    
    
}

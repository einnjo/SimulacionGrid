package proyectosimulacion;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Juan Jose Leñero
 */
public class Evento {
    public static final int TIPO_LLEGADA = 0;
    public static final int TIPO_SALIDA = 1;
   
    private int tipo;
    private Double tiempo;
    private Tarea tarea;
    
  
    public Evento(int tipo, Double tiempo, Tarea tarea) {
        this.tipo = tipo;
        this.tiempo = tiempo;
        this.tarea = tarea;
    }
    
    public static ArrayList<Evento> cargaEventosIniciales(String path){
        try{
            ArrayList<Evento> eventos = new ArrayList<Evento>(140000);
            
            BufferedReader br = new BufferedReader(new FileReader(path));
            String linea = br.readLine();
            String[] valores = new String[5];
            int id;
            Double tiempoLlegada = 0.0;
            Double runTime;
            int reqProcs;
            Tarea tarea;
            Evento evento;
            
            while(linea != null){
                valores = linea.split("\t");
                id = Integer.parseInt(valores[0]);
                runTime = Double.parseDouble(valores[1]);
                reqProcs = Integer.parseInt(valores[3]);
                tarea = new Tarea(id, tiempoLlegada, runTime, reqProcs);
                evento = new Evento(TIPO_LLEGADA, tiempoLlegada, tarea);
                eventos.add(0,evento);
                // genera el siguiente tiempo de llegada
                tiempoLlegada += ((-1 * Math.log(Math.random())) / Simulador.LAMBDA);
                // lee la siguiente linea
                linea = br.readLine();
            }
            
            return eventos;
            
        }catch(IOException ioe){
            ioe.printStackTrace();
            return null;
        }
        
    }
    
    public static void InsertaEnOrden(Evento evento, ArrayList<Evento> eventos){
        Double tiempoInsercion = evento.getTiempo();
        Double tiempoEnI;
        int indice=-1;
        for(int i = eventos.size()-1;i>=0;i--){
            tiempoEnI = eventos.get(i).getTiempo();
            if(tiempoInsercion<tiempoEnI){
                indice = i+1;
                eventos.add(i+1, evento);
                break;
            }
        }
        // si ningun evento en la lista tiene un tiempo mayor al del evento a insertar
        if(indice ==-1){
            // el evento a agregar es el mas antiguo
            eventos.add(0, evento);
        }
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public Double getTiempo() {
        return tiempo;
    }

    public void setTiempo(Double tiempo) {
        this.tiempo = tiempo;
    }

    public Tarea getTarea() {
        return tarea;
    }

    public void setTarea(Tarea tarea) {
        this.tarea = tarea;
    }
    
    
    
    
}

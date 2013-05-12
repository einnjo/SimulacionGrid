package proyectosimulacion;

import java.io.IOException;

/**
 *
 * @author Juan Jose Leñero
 */
public class GeneraSimulacion {
    public static final String PATH_ARCHIVO = "/home/juanjo/NetBeansProjects/ProyectoSimulacion/listaTareas.txt";
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        int[] procsSimulacion = {10, 50, 100, 500, 1000};
        int nProcs;
        Broker broker;
        Simulador sim;
        System.out.println("\n----------------------------------------------------------SIMULACION SISTEMA GRID----------------------------------------------------------");
        System.out.printf("%20s%20s%20s%20s%20s%20s%20s%n","[nMax]","[broker]","[tTotal]","[tSistemaProm]","[makespan]","[colaProm]","[colaMax]");
        for(int i=0; i<procsSimulacion.length;i++){
            
            nProcs = procsSimulacion[i];
            

            broker = new BrokerRoundRobin(nProcs);
           // System.out.println("Comenzando simulacion con "+nProcs+" procesadores y broker "+broker.getTipo());
            sim = new Simulador(nProcs, Evento.cargaEventosIniciales(PATH_ARCHIVO), broker);
            sim.simula();

            broker = new BrokerList(nProcs);
           // System.out.println("Comenzando simulacion con "+nProcs+" procesadores y broker "+broker.getTipo());
            sim = new Simulador(nProcs, Evento.cargaEventosIniciales(PATH_ARCHIVO), broker);
            sim.simula();


            broker = new BrokerParetoFractal(nProcs);
         //   System.out.println("Comenzando simulacion con "+nProcs+" procesadores y broker "+broker.getTipo());
            sim = new Simulador(nProcs, Evento.cargaEventosIniciales(PATH_ARCHIVO), broker);
            sim.simula();
        }
        
    }
}

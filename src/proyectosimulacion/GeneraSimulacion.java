package proyectosimulacion;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author Juan Jose Leñero
 */
public class GeneraSimulacion {
    public static final String PATH_ARCHIVO = "/home/juanjo/NetBeansProjects/ProyectoSimulacion/listaTareas.txt";
    public static final String PATH_RESULTADO = "/home/juanjo/NetBeansProjects/ProyectoSimulacion/resultados.txt";
    
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
        // prepara el archivo de resultados
        try{
            FileWriter write = new FileWriter(GeneraSimulacion.PATH_RESULTADO,true);
            PrintWriter print_line = new PrintWriter(write);
            print_line.printf("%s\t%s\t%s\t%s\t%s\t%s\t%s%n", "nMax","broker","tTotal","tSistemaProm","makespan","colaProm","colaMax");
            print_line.close();
        }catch(IOException ioe){
            System.out.println("ERROR DE ESCRITURA DE RESULTADOS");
        }
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

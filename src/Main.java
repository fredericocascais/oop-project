import aco.Ant;
import aco.AntMoveEvent;
import aco.InputParameters;
import aco.Pheromones;
import graph.*;
import pec.Event;
import pec.IEvent;
import pec.PEC;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.*;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {


        ArrayList<HamiltonianCycle> hamiltonian_cycles = new ArrayList<>();
        HamiltonianCycle bestHamiltonianCycle = new HamiltonianCycle(new ArrayList<>(),0);
        ArrayList<HamiltonianCycle> top_hamiltonian_cycles = new ArrayList<>(5);
        PrintStream ps = new PrintStream(new FileOutputStream("sim.txt"));
        //Check input aguments
        InputParameters.checkArgs(args);

        if ("-r".equals(args[0])) { //get arguments directly from command line

            int tot_nodes = Integer.parseInt(args[1]);
            int max_weight = Integer.parseInt(args[2]);
            WeightedGraph graph = new WeightedGraph(tot_nodes);
            graph.createRandomGraph(max_weight);

            double curr_time = 0.0;
            int nest_node = Integer.parseInt(args[3]); //nest node
            double alpha = Double.parseDouble(args[4]);
            double beta = Double.parseDouble(args[5]);
            double delta = Double.parseDouble(args[6]);
            double eta = Double.parseDouble(args[7]); //n, evaporation event
            double rho = Double.parseDouble(args[8]); //p, evaporation event
            double gamma = Double.parseDouble(args[9]); //y, pheromone level
            int colony_size = Integer.parseInt(args[10]);
            double sim_time = Double.parseDouble(args[11]);
            double time_interval = sim_time/20;
            Node node_1 = graph.getNode(nest_node-1);

            //Print input parameters to terminal
            InputParameters.printInputParameters(tot_nodes,nest_node,alpha,beta,delta,eta,rho,gamma,colony_size,sim_time,ps);
            //Print graph to terminal
            InputParameters.printGraph(tot_nodes, graph,ps);


            Pheromones pheromones = new Pheromones(graph.getMaxEdges());
            PEC pec = new PEC();

            ArrayList<Ant> ants = new ArrayList<>(colony_size);
            for(int i=0; i < colony_size; i++){
                Ant ant = new Ant(node_1);
                ants.add(i, ant);
                Edge next_edge = ant.getNextChosenEdge(pheromones, alpha, beta);
                pec.addEvent(new AntMoveEvent(curr_time,graph,ant,next_edge,delta));
            }
            int observation_number=0;
            int number_move_events=0;
            int number_evap_events=0;
            //System.out.println("Event added to PEC : "+pec.getCurrEvent().getEventType()+" for "+pec.getCurrEvent().getEventTime());
            while (curr_time < sim_time) {

                if(curr_time>=time_interval ){
                    observation_number+=1;
                    //Choosing best hamiltonian cycle
                    bestHamiltonianCycle = HamiltonianCycle.bestCycle(hamiltonian_cycles, bestHamiltonianCycle);
                    //top 5 cycles
                    HamiltonianCycle.topCycles(hamiltonian_cycles, top_hamiltonian_cycles, bestHamiltonianCycle);
                    //Print
                    InputParameters.printSteps(observation_number,time_interval,number_move_events,number_evap_events,bestHamiltonianCycle,top_hamiltonian_cycles,ps);
                    time_interval+= sim_time/20;
                }

                IEvent Event1 = pec.getNextEvent();
                curr_time=Event1.getEventTime();
                Ant ant = (Ant) Event1.executeEvent();

                if(Event1.getEventType().equals("ant_move")){
                    number_move_events+=1;
                }
                if(Event1.getEventType().equals("evaporation")){
                    number_evap_events+=1;
                }

                //Check Hamiltonian
                if (ant.getPathEdges().size() >= tot_nodes) {
                    if (ant.pathIsHamiltonean(tot_nodes)) {
                        // create evap events
                        ArrayList<Integer> hamilt_path = new ArrayList<>(ant.getPath());
                        LinkedList<Edge> pathEdges = ant.getPathEdges();
                        double t_weight=0.0;

                        for (Edge edge : pathEdges) {
                            t_weight += edge.getWeight();
                        }
                        int total_weight = (int) t_weight;

                        HamiltonianCycle newHamiltonianCycle = new HamiltonianCycle(hamilt_path,total_weight);
                        boolean contains_cycle = false;
                        for (HamiltonianCycle hamiltonian_cycle : hamiltonian_cycles) {
                            if (hamiltonian_cycle.equals(newHamiltonianCycle)) {
                                contains_cycle = true;
                                break;
                            }
                        }
                        if(!contains_cycle){
                            hamiltonian_cycles.add(newHamiltonianCycle);
                        }
                        ant.resetPath();
                    }
                }
                Edge next_edge = ant.getNextChosenEdge(pheromones, alpha, beta);
                pec.addEvent(new AntMoveEvent(curr_time, graph, ant, next_edge, delta));
            }
            observation_number+=1;
            InputParameters.printSteps(observation_number,time_interval,number_move_events,number_evap_events,bestHamiltonianCycle,top_hamiltonian_cycles,ps);
            //InputParameters.output("\nHamiltonian cycles with weight: "+ hamiltonian_cycles.toString().replace("[","").replace("]","") , System.out,ps);

        }
        if ("-f".equals(args[0])) { //reads from file if argument '-f' is used

            File input_file = new File(args[1]);
            Scanner scanner = new Scanner(input_file);

            String first_line = scanner.nextLine();  //Get parameters from 1st line
            String[] aux = first_line.split(" ");

            int tot_nodes = Integer.parseInt(aux[0]);
            int nest_node = Integer.parseInt(aux[1]);
            double alpha = Double.parseDouble(aux[2]);
            double beta = Double.parseDouble(aux[3]);
            double delta = Double.parseDouble(aux[4]);
            double eta = Double.parseDouble(aux[5]); //n, evaporation event
            double rho = Double.parseDouble(aux[6]); //p, evaporation event
            double gamma = Double.parseDouble(aux[7]); //y, pheromone level
            int colony_size = Integer.parseInt(aux[8]);
            double sim_time = Double.parseDouble(aux[9]);
            double curr_time = 0.0;
            double time_interval = sim_time/20;
            WeightedGraph graph = new WeightedGraph(tot_nodes);

            //Print input parameters to terminal
            InputParameters.printInputParameters(tot_nodes,nest_node,alpha,beta,delta,eta,rho,gamma,colony_size,sim_time,ps);

            //Read graph
            String[] line;
            line = new String[tot_nodes];
            int nbr_lines=0;

            while(scanner.hasNextLine()){    //Get graph from remaining lines
                String fileline = scanner.nextLine();
                line[nbr_lines]=fileline;
                nbr_lines+=1;
            }
            //Create graph
            graph.createGivenGraph(tot_nodes, line, graph);
            //Print graph
            InputParameters.printGraph(tot_nodes,graph,ps);

            Node node_1 = graph.getNode(nest_node-1);
            Pheromones pheromones = new Pheromones(graph.getMaxEdges());
            PEC pec = new PEC();
            ArrayList<Ant> ants = new ArrayList<>(colony_size);
            for(int i=0; i < colony_size; i++){
                Ant ant = new Ant(node_1);
                ants.add(i, ant);
                Edge next_edge = ant.getNextChosenEdge(pheromones, alpha, beta);
                pec.addEvent(new AntMoveEvent(curr_time,graph,ant,next_edge,delta));
            }
            int observation_number=0;
            int number_move_events=0;
            int number_evap_events=0;
            //System.out.println("Event added to PEC : "+pec.getCurrEvent().getEventType()+" for "+pec.getCurrEvent().getEventTime());
            while (curr_time < sim_time) {

                if(curr_time>=time_interval ){
                    observation_number+=1;
                    //Choosing best hamiltonian cycle
                    bestHamiltonianCycle = HamiltonianCycle.bestCycle(hamiltonian_cycles, bestHamiltonianCycle);
                    //top 5 cycles
                    HamiltonianCycle.topCycles(hamiltonian_cycles, top_hamiltonian_cycles, bestHamiltonianCycle);
                    //Print
                    InputParameters.printSteps(observation_number,time_interval,number_move_events,number_evap_events,bestHamiltonianCycle,top_hamiltonian_cycles,ps);
                    time_interval+= sim_time/20;
                }

                IEvent Event1 = pec.getNextEvent();
                curr_time=Event1.getEventTime();
                Ant ant = (Ant) Event1.executeEvent();
                if(Event1.getEventType().equals("ant_move")){
                    number_move_events+=1;
                }
                if(Event1.getEventType().equals("evaporation")){
                    number_evap_events+=1;
                }
                //Check Hamiltonian
                if (ant.getPathEdges().size() >= tot_nodes) {
                    if (ant.pathIsHamiltonean(tot_nodes)) {
                        // create evap events
                        ArrayList<Integer> hamilt_path = new ArrayList<>(ant.getPath());
                        LinkedList<Edge> pathEdges = ant.getPathEdges();
                        double t_weight=0.0;

                        for (Edge edge : pathEdges) {
                            t_weight += edge.getWeight();
                        }
                        int total_weight = (int) t_weight;
                        HamiltonianCycle newHamiltonianCycle = new HamiltonianCycle(hamilt_path,total_weight);

                        boolean contains_cycle = false;
                        for (HamiltonianCycle hamiltonian_cycle : hamiltonian_cycles) {
                            if (hamiltonian_cycle.equals(newHamiltonianCycle)) {
                                contains_cycle = true;
                                break;
                            }
                        }
                        if(!contains_cycle){
                            hamiltonian_cycles.add(newHamiltonianCycle);
                        }

                        //System.out.println("HAMILTON");
                        ant.resetPath();
                    }
                }
                Edge next_edge = ant.getNextChosenEdge(pheromones, alpha, beta);
                pec.addEvent(new AntMoveEvent(curr_time, graph, ant, next_edge, delta));
            }
            observation_number+=1;
            InputParameters.printSteps(observation_number,time_interval,number_move_events,number_evap_events,bestHamiltonianCycle,top_hamiltonian_cycles,ps);
            //InputParameters.output("\nHamiltonian cycles with weight: "+ hamiltonian_cycles.toString().replace("[","").replace("]","") , System.out,ps);
        }
        ps.close();
    }
}
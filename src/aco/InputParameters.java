package aco;

import graph.Edge;
import graph.HamiltonianCycle;
import graph.WeightedGraph;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class InputParameters {

    public static void output(String string, PrintStream ps1, PrintStream ps2){
        ps1.println(string);
        ps2.println(string);
    }
    public static void checkArgs(String[] args){
        if(args.length<2){
            System.out.println("Arguments Error: Not Enough Arguments");
            System.exit(1);
        }
        if(!"-r".equals(args[0]) && !"-f".equals(args[0])){
            System.out.println("Invalid Option: Must Use '-r' or '-f'");
        }
    }
    public static void printInputParameters(int tot_nodes, int nest_node, double alpha,
                                            double beta, double delta, double eta, double rho,
                                            double gamma, int colony_size, double sim_time, PrintStream ps){

        output("Input Parameters:", System.out, ps);
        output("                  " + tot_nodes + ": number of nodes in the graph", System.out, ps);
        output("                  " + nest_node + ": the nest node", System.out, ps);
        output("                  " + alpha + ": alpha, ant move event", System.out, ps);
        output("                  " + beta + ": beta, ant move event", System.out, ps);
        output("                  " + delta + ": delta, ant move event", System.out, ps);
        output("                  " + eta + ": eta, pheromone evaporation event", System.out, ps);
        output("                  " + rho + ": rho, pheromone evaporation event", System.out, ps);
        output("                  " + gamma + ": pheromone level", System.out, ps);
        output("                  " + colony_size + ": ant colony size", System.out, ps);
        output("                  " + sim_time + ": final instant", System.out, ps);

    }
    public static void printGraph(int tot_nodes, WeightedGraph graph, PrintStream ps){

        output("with graph: ",System.out, ps);
        for(int nodes = 0; nodes <= tot_nodes-1; nodes++) {
            List<Integer> linked_nodes = graph.getNode(nodes).getLinked();
            List<Edge> edges = graph.getNode(nodes).getEdges();
            //double[] node_row;
            //node_row= new double[tot_nodes];
            int[] node_row;
            node_row= new int[tot_nodes];


            for(int node = 0; node <= tot_nodes-1; node++) {
                int flag=1;
                for(int linked_node=0; linked_node <= linked_nodes.size()-1; linked_node++){
                    if(linked_nodes.get(linked_node)== node){
                        flag = 0;
                        int weight_int = (int) edges.get(linked_node).getWeight();
                        node_row[node] = weight_int;
                        //node_row[node] = edges.get(linked_node).getWeight();
                    }
                }

                if(flag==1){
                    //node_row[node] = 0.0;
                    node_row[node] = 0;
                }
            }
            output("                  " + Arrays.toString(node_row),System.out, ps);
        }
    }
    public static void printSteps(int observation_number, double time_interval , int number_move_events , int number_evap_events, HamiltonianCycle hamiltonianCycle,
                                  ArrayList<HamiltonianCycle> top_hamiltonian_cycles, PrintStream ps){
        //Sort top hamiltonian cycles according to weight
        top_hamiltonian_cycles.sort(Comparator.comparing(HamiltonianCycle::getTotalWeight));
        //Print
        InputParameters.output("Observation " + observation_number + ":",System.out,ps);
        InputParameters.output("                  Present instant:               " + time_interval, System.out, ps);
        InputParameters.output("                  Number of move events:         " + number_move_events, System.out, ps);
        InputParameters.output("                  Number of evaporation events:  " + number_evap_events, System.out, ps);
        InputParameters.output("                  Top candidate cycles:" , System.out, ps);
        //System.out.print("                  Top candidate cycles:");
        //ps.print("                  Top candidate cycles:");
        for (HamiltonianCycle top_hamiltonian_cycle : top_hamiltonian_cycles){
            InputParameters.output("                                                 "+top_hamiltonian_cycle, System.out,ps);
        }
        InputParameters.output("\n                  Best Hamiltonian:              " + hamiltonianCycle, System.out, ps);
    }


}

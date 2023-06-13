import aco.Ant;
import aco.AntMoveEvent;
import aco.Pheromones;
import graph.*;
import pec.Event;
import pec.IEvent;
import pec.PEC;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static double edgeFavOutcome(double alpha, double beta, double pheromone, double weight){
        return  (alpha + pheromone) / (beta + weight);
    }
    public static void printInputParameters(int tot_nodes, int nest_node, double alpha,
                                            double beta, double delta, double eta, double rho,
                                            double gamma, int colony_size, double sim_time){
        System.out.println("Input Parameters:");
        System.out.println("                  " + tot_nodes + ": number of nodes in the graph");
        System.out.println("                  " + nest_node + ": the nest node");
        System.out.println("                  " + alpha + ": alpha, ant move event");
        System.out.println("                  " + beta + ": beta, ant move event");
        System.out.println("                  " + delta + ": delta, ant move event");
        System.out.println("                  " + eta + ": eta, pheromone evaporation event");
        System.out.println("                  " + rho + ": rho, pheromone evaporation event");
        System.out.println("                  " + gamma + ": pheromone level");
        System.out.println("                  " + colony_size + ": ant colony size");
        System.out.println("                  " + sim_time + ": final instant");
    }
    public static void printGraph(int tot_nodes, WeightedGraph graph){
        System.out.println("with graph:");
        for(int n_nodes = 0; n_nodes <= tot_nodes-1; n_nodes++) {
            List<Integer> arr = graph.getNode(n_nodes).getLinked();
            List<Edge> arr2 = graph.getNode(n_nodes).getEdges();
            double d[];
            d = new double[tot_nodes];

            for(int n_nodes2 = 0; n_nodes2 <= tot_nodes-1; n_nodes2++) {
                int flag=1;
                for(int nnodes3=0; nnodes3 <= arr.size()-1; nnodes3++){
                    if(arr.get(nnodes3)== n_nodes2){
                        flag = 0;
                        d[n_nodes2] = arr2.get(nnodes3).getWeight();
                    }
                }

                if(flag==1){d[n_nodes2] = 0.0;}
            }
            System.out.println("                  " + Arrays.toString(d));
        }
    }
    public static void main(String[] args) throws FileNotFoundException {

        if(args.length<2){
            System.out.println("Arguments Error");
            System.exit(1);
        }
        if ("-r".equals(args[0])) { //get arguments directly from command line

            int tot_nodes = Integer.parseInt(args[1]);
            int max_weight = Integer.parseInt(args[2]);
            WeightedGraph graph = new WeightedGraph(tot_nodes);
            graph.createRandomGraph(max_weight);

            double curr_time = 0.0;
            int ants_simulated = 0;
            int nest_node = Integer.parseInt(args[3]); //nest node
            double alpha = Double.parseDouble(args[4]);
            double beta = Double.parseDouble(args[5]);
            double delta = Double.parseDouble(args[6]);
            double eta = Double.parseDouble(args[7]); //n, evaporation event
            double rho = Double.parseDouble(args[8]); //p, evaporation event
            double gamma = Double.parseDouble(args[9]); //y, pheromone level
            int colony_size = Integer.parseInt(args[10]);
            double sim_time = Double.parseDouble(args[11]);
            Node node_1 = graph.getNode(nest_node-1);

            //Print input parameters to terminal
            printInputParameters(tot_nodes,nest_node,alpha,beta,delta,eta,rho,gamma,colony_size,sim_time);
            //Print graph to terminal
            printGraph(tot_nodes, graph);
            System.out.println("Nest node: "+node_1);
            Pheromones pheromones = new Pheromones(graph.getMaxEdges());
            PEC pec = new PEC();

            for (int n_ant = 1; n_ant <= 1; n_ant++) {
                Ant ant = new Ant(node_1);

                Edge next_edge = ant.getNextChosenEdge(pheromones, alpha, beta);


                pec.addEvent(new AntMoveEvent(curr_time, graph, ant, next_edge, delta));
                System.out.println("Event added to PEC : "+pec.getCurrEvent().getEventType()+" for "+pec.getCurrEvent().getEventTime());

                //pec.getNextEvent().executeEvent();
                IEvent Event1 = pec.getNextEvent();
                curr_time+=Event1.getEventTime();
                Event1.executeEvent();

                //while (curr_time < sim_time && ants_simulated < colony_size) {
                for (int k = 0; k <= 100; k++) {

                    if (ant.getPathEdges().size() >= tot_nodes) {
                        if (ant.pathIsHamiltonean(tot_nodes)) {
                            // create evap events
                            System.out.println("HAMILTON");
                            System.out.println(ant.getPathEdges());
                            break;
                        }
                    }

                    next_edge = ant.getNextChosenEdge(pheromones, alpha, beta);
                    System.out.println("next edgge before setevent: "+next_edge);
                    pec.addEvent(new AntMoveEvent(curr_time, graph, ant, next_edge, delta));

                    //pec.getNextEvent().executeEvent();
                    IEvent Event2 = pec.getNextEvent();
                    curr_time+=Event2.getEventTime();
                    Event2.executeEvent();
                    //System.out.println("Event added to PEC : "+pec.getCurrEvent().getEventType()+" for "+pec.getCurrEvent().getEventTime());



                    //break;
                }
            }
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
            int ants_simulated = 0;

            WeightedGraph graph = new WeightedGraph(tot_nodes);

            //Print input parameters to terminal
            printInputParameters(tot_nodes,nest_node,alpha,beta,delta,eta,rho,gamma,colony_size,sim_time);

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
            printGraph(tot_nodes,graph);

            //START
            Node node_1 = graph.getNode(nest_node-1);
            System.out.println("Nest node: "+node_1);
            Pheromones pheromones = new Pheromones(graph.getMaxEdges());
            PEC pec = new PEC();
            ArrayList<Ant> ants = new ArrayList<>(colony_size);
            for(int i=0; i < colony_size; i++){
                Ant ant = new Ant(node_1);
                ants.add(i, ant);
                Edge next_edge = ant.getNextChosenEdge(pheromones, alpha, beta);
                pec.addEvent(new AntMoveEvent(curr_time,graph,ant,next_edge,delta));            }
            //for (int n_ant = 1; n_ant <= 1; n_ant++) {

                //Edge next_edge = ant.getNextChosenEdge(pheromones, alpha, beta);
                //pec.addEvent(new AntMoveEvent(curr_time, graph, ant, next_edge, delta));
                System.out.println("Event added to PEC : "+pec.getCurrEvent().getEventType()+" for "+pec.getCurrEvent().getEventTime());

                //pec.getNextEvent().executeEvent();
                //IEvent Event1 = pec.getNextEvent();
                //curr_time+=Event1.getEventTime();
               // Event1.executeEvent();

                while (curr_time < sim_time) {
                //for (int k = 0; k <= 100; k++) {
                    IEvent Event1 = pec.getNextEvent();
                    curr_time=Event1.getEventTime();
                    Ant ant = (Ant) Event1.executeEvent();
                    System.out.println("current time: "+curr_time);

                    //Check Hamiltonean
                    if (ant.getPathEdges().size() >= tot_nodes) {
                        if (ant.pathIsHamiltonean(tot_nodes)) {
                            // create evap events
                            System.out.println("HAMILTON");
                            System.out.println(ant.getPathEdges());
                            break;
                        }
                    }

                    Edge next_edge = ant.getNextChosenEdge(pheromones, alpha, beta);
                    System.out.println("next edgge before setevent: "+next_edge);
                    pec.addEvent(new AntMoveEvent(curr_time, graph, ant, next_edge, delta));

                    //pec.getNextEvent().executeEvent();
                    //IEvent Event2 = pec.getNextEvent();
                    //curr_time+=Event2.getEventTime();
                    //Event2.executeEvent();
                    //System.out.println("Event added to PEC : "+pec.getCurrEvent().getEventType()+" for "+pec.getCurrEvent().getEventTime());



                    //break;
                }
            //}

        }

    }
}
package aco;

import graph.Edge;
import graph.Node;
import graph.WeightedGraph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class InputParameters {
    private int nest_node;
    private WeightedGraph graph;
    private Node node_1;
    private int tot_nodes;
    private double alpha ;
    private double beta ;
    private double delta ;
    private double eta ; //n, evaporation event
    private double rho; //p, evaporation event
    private double gamma; //y, pheromone level

    private double sim_time;
    private double curr_time;

    private int colony_size;
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
        for(int nodes = 0; nodes <= tot_nodes-1; nodes++) {
            List<Integer> linked_nodes = graph.getNode(nodes).getLinked();
            List<Edge> edges = graph.getNode(nodes).getEdges();
            double[] node_row;
            node_row= new double[tot_nodes];

            for(int n_nodes2 = 0; n_nodes2 <= tot_nodes-1; n_nodes2++) {
                int flag=1;
                for(int nnodes3=0; nnodes3 <= linked_nodes.size()-1; nnodes3++){
                    if(linked_nodes.get(nnodes3)== n_nodes2){
                        flag = 0;
                        node_row[n_nodes2] = edges.get(nnodes3).getWeight();
                    }
                }

                if(flag==1){node_row[n_nodes2] = 0.0;}
            }
            System.out.println("                  " + Arrays.toString(node_row));
        }
    }

    public WeightedGraph getGraph(){return graph;}
    public int getDest_node(){return nest_node-1;}
    public int getTot_nodes(){return tot_nodes;}
    public int getColony_size(){return colony_size;}
    public double getAlpha(){return alpha;}
    public double getBeta(){return beta;}
    public double getDelta(){return delta;}

    public double getCurr_time() {return curr_time;}
    public double getSim_time(){return sim_time;}
    public Node getNode_1(){return node_1;}
}

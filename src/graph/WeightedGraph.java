package graph;

import simulation.InputParameters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class WeightedGraph extends AbstractGraph{
    private static WeightedGraph graph;
    private double totalWeight = 0;

    private WeightedGraph(int n_nodes) {
        super(n_nodes);
    }

    public static WeightedGraph getGraph(int totalNodes){
        if (graph == null) { 
            graph = new WeightedGraph(totalNodes);
        }
        return graph;
    }

    public static WeightedGraph getGraph(){
        return graph;
    }

    public double getTotalWeight(){
        return totalWeight;
    }

    @Override
    public void addEdgeToList(int n1, int n2, double weight) {
        this.adjList[n1].addEdgeToNode(n2, weight);
        this.adjList[n2].addEdgeToNode(n1, weight);
        totalWeight+=weight;
    }
    @Override
    public void addEdgeToList(int n1, int n2){
        throw new RuntimeException("Error addEdgeToList(int n1, int n2): Cannot add a Unweighted Edge to an Weighted Graph.\n");
    }

    @Override
    public void createRandomGraph(int max_weight){

        Random rand = new Random();

        max_edges = rand.nextInt(max_edges - n_nodes) + n_nodes;
        ArrayList<Integer> hamiltonean_path = generateRandomHamiltoneanPath();

        for (int j = 0; j < hamiltonean_path.size(); j++) {
            if( j + 1 == hamiltonean_path.size()) break;
            int node1 = hamiltonean_path.get(j);
            int node2 = hamiltonean_path.get(j + 1);

            double weight = rand.nextInt(max_weight - 1) + 1;

            addEdgeToList( node1, node2 , weight);
        }

        int i = n_nodes;
        while (i <= max_edges) {
            int node1 = rand.nextInt(n_nodes);
            int node2 = rand.nextInt(n_nodes);

            if( node1 == node2 ) continue;
            if( adjList[node1].getLinkedNodes().contains(node2) ) continue;

            double weight = rand.nextInt(max_weight - 1) + 1;

            addEdgeToList( node1, node2 , weight);
            //System.out.println(weight);

            i++;
        }

    }

    public void printGraph(){
        System.out.println("\t\t with graph:");

        for (int i = 0; i <= getTotalNodes() - 1; i++){
            List<Integer> linked_nodes = getNode(i).getLinkedNodes();
            List<Edge> edges = getNode(i).getEdges();
            double[] node_row;
            node_row = new double[getTotalNodes()];

            for(int node = 0; node <= getTotalNodes() - 1; node++) {
                int flag=1;
                for(int linked_node = 0; linked_node <= linked_nodes.size() - 1; linked_node++){
                    if(linked_nodes.get(linked_node) == node){
                        flag = 0;
                        node_row[node] = edges.get(linked_node).getWeight();
                    }
                }
                if(flag==1){node_row[node] = 0.0;}
            }
            System.out.println("\t\t\t" + Arrays.toString(node_row));
        }
    }
    @Override
    public void createGivenGraph(InputParameters parameters){

        String[] weight;
        String[] line = parameters.getFileMatrix();
        for (int og_node=0; og_node<= parameters.getTotalNodes() - 1; og_node++) {
            //System.out.println("og node: " + og_node);
            weight = line[og_node].split(" ");
            for(int dest_node=0; dest_node <= weight.length - 1; dest_node++) {
                if(!weight[dest_node].equals("0")) {
                    //System.out.println("      dest node " + dest_node + ": "+ "weight = " + Double.parseDouble(weight[dest_node]));
                    graph.addEdgeToList(og_node,dest_node,Double.parseDouble(weight[dest_node]));
                }
            }

        }

    }
    @Override
    public void createRandomGraph(){
        throw new RuntimeException("Error createRandomGraph(): Cannot create a Random Weighted Graph without weights.\n");
    }




}

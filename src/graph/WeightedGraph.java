package graph;

import simulation.InputParameters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * The WeightedGraph class represents a weighted graph.
 * It extends the AbstractGraph class and provides additional methods for managing weighted edges.
 */
public class WeightedGraph extends AbstractGraph {
    private static WeightedGraph graph;
    private double totalWeight = 0;

    /**
     * Constructs a WeightedGraph object with the specified number of nodes.
     *
     * @param totalNodes The total number of nodes in the graph.
     */
    private WeightedGraph(int totalNodes) {
        super(totalNodes);
    }

    /**
     * Gets the singleton instance of the WeightedGraph.
     *
     * @param totalNodes The total number of nodes in the graph.
     * @return The singleton instance of the WeightedGraph.
     */
    public static WeightedGraph getGraph(int totalNodes) {
        if (graph == null) {
            graph = new WeightedGraph(totalNodes);
        }
        return graph;
    }

    /**
     * Gets the singleton instance of the WeightedGraph.
     *
     * @return The singleton instance of the WeightedGraph.
     */
    public static WeightedGraph getGraph() {
        return graph;
    }

    /**
     * Gets the total weight of the graph.
     *
     * @return The total weight of the graph.
     */
    public double getTotalWeight() {
        return totalWeight;
    }

    /**
     * Adds a weighted edge between the specified nodes in the graph.
     *
     * @param n1     The ID of the first node.
     * @param n2     The ID of the second node.
     * @param weight The weight of the edge.
     */
    @Override
    public void addEdgeToList(int n1, int n2, double weight) {
        this.adjList[n1].addEdgeToNode(n2, weight);
        this.adjList[n2].addEdgeToNode(n1, weight);
        totalWeight += weight;
    }

    /**
     * Throws an exception as it is not supported in WeightedGraph.
     *
     * @param n1 The ID of the first node.
     * @param n2 The ID of the second node.
     */
    @Override
    public void addEdgeToList(int n1, int n2) {
        throw new RuntimeException("Error addEdgeToList(int n1, int n2): Cannot add an Unweighted Edge to a Weighted Graph.\n");
    }


        /**
     * Creates a random graph with weighted edges.
     *
     * @param max_weight The maximum weight of the edges.
     */
    @Override
    public void createRandomGraph(int max_weight) {
        Random rand = new Random();

        // Determine randomly the max amount of edges going to be added
        max_edges = rand.nextInt(max_edges - n_nodes) + n_nodes;
        ArrayList<Integer> hamiltonian_path = generateRandomHamiltonianPath();

        // Unite nodes to create a Hamiltonian cycle in the graph
        for (int j = 0; j < hamiltonian_path.size(); j++) {
            if (j + 1 == hamiltonian_path.size()) break;
            int node1 = hamiltonian_path.get(j);
            int node2 = hamiltonian_path.get(j + 1);

            double weight = rand.nextInt(max_weight - 1) + 1;

            addEdgeToList(node1, node2, weight);
        }

        // Unite nodes randomly
        int i = n_nodes;
        while (i <= max_edges) {
            int node1 = rand.nextInt(n_nodes);
            int node2 = rand.nextInt(n_nodes);

            if (node1 == node2) continue;
            if (adjList[node1].getLinkedNodes().contains(node2)) continue;

            double weight = rand.nextInt(max_weight - 1) + 1;

            addEdgeToList(node1, node2, weight);
            i++;
        }
    }


     /**
     * Prints the graph representation.
     * Each row represents a node, and the entries in each row represent the weights of the edges.
     */
    public void printGraph() {
        System.out.println("\t with graph:");

        for (int i = 0; i <= getTotalNodes() - 1; i++) {
            List<Integer> linked_nodes = getNode(i).getLinkedNodes();
            List<Edge> edges = getNode(i).getEdges();
            int[] node_row;
            node_row = new int[getTotalNodes()];

            for (int node = 0; node <= getTotalNodes() - 1; node++) {
                int flag = 1;
                for (int linked_node = 0; linked_node <= linked_nodes.size() - 1; linked_node++) {
                    if (linked_nodes.get(linked_node) == node) {
                        flag = 0;
                        node_row[node] = (int) Math.round(edges.get(linked_node).getWeight());
                    }
                }
                if (flag == 1) {
                    node_row[node] = 0;
                }
            }
            System.out.println("\t\t\t\t" + Arrays.toString(node_row).replaceAll("[,\\[\\]]", ""));
        }
    }
    /**
     * Creates a graph based on the given input parameters.
     *
     * @param parameters The input parameters specifying the graph structure.
     */
    @Override
    public void createGivenGraph(InputParameters parameters) {
        String[] line = parameters.getFileMatrix();
        String[] weights_in_line;

        for (int origin_node = 0; origin_node <= parameters.getTotalNodes() - 1; origin_node++) {
            weights_in_line = line[origin_node].split(" ");
            for(int dest_node = origin_node + 1; dest_node <= weights_in_line.length - 1; dest_node++) {
                if(!weights_in_line[dest_node].equals("0")) {
                    graph.addEdgeToList(origin_node,dest_node,Double.parseDouble(weights_in_line[dest_node]));
                }
            }

        }
    }
     /**
     * Throws a runtime exception indicating that a random weighted graph cannot be created without weights.
     *
     * @throws RuntimeException Indicates that a random weighted graph cannot be created without weights.
     */
    @Override
    public void createRandomGraph() {
        throw new RuntimeException("Error createRandomGraph(): Cannot create a Random Weighted Graph without weights.\n");
    }
}

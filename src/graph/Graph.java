package graph;

import simulation.InputParameters;

import java.util.ArrayList;
import java.util.Random;

/**
 * The Graph class represents an unweighted graph.
 * It extends the AbstractGraph class and provides implementations for specific graph operations.
 */
public class Graph extends AbstractGraph{

    /**
     * Constructs a Graph object with the specified number of nodes.
     *
     * @param n_nodes The number of nodes in the graph.
     */
    public Graph(int n_nodes) {
        super(n_nodes);
    }

    /**
     * Adds an unweighted edge between the specified nodes.
     *
     * @param n1 The ID of the first node.
     * @param n2 The ID of the second node.
     */
    @Override
    public void addEdgeToList(int n1, int n2 ){
        this.adjList[ n1 ].addEdgeToNode( n2 );
        this.adjList[ n2 ].addEdgeToNode( n1 );
    }

    /**
     * Adds a weighted edge between the specified nodes.
     * This operation is not supported in an unweighted graph.
     *
     * @param n1 The ID of the first node.
     * @param n2 The ID of the second node.
     * @param weight The weight of the edge.
     */
    @Override
    public void addEdgeToList(int n1, int n2, double weight) {
        throw new RuntimeException("Error addEdgeToList(int n1, int n2, double weight): Cannot add a Weighted Edge to an Unweighted Graph.\n");
    }

    /**
     * Creates the graph based on the given input parameters.
     * This operation is not supported in an unweighted graph.
     *
     * @param parameters The input parameters for creating the graph.
     */
    @Override
    public void createGivenGraph(InputParameters parameters) {
        // Implementation specific to a weighted graph
    }

    /**
     * Creates a random unweighted graph.
     * Random edges are added to connect nodes, and a random Hamiltonian path is used as a base.
     */
    @Override
    public void createRandomGraph(){

        Random rand = new Random();

        int num_edges = rand.nextInt(max_edges - n_nodes) + n_nodes;
        ArrayList<Integer> hamiltonian_path = generateRandomHamiltonianPath();

        for (int j = 0; j < hamiltonian_path.size(); j++) {
            if( j + 1 == hamiltonian_path.size()) break;
            int node1 = hamiltonian_path.get(j);
            int node2 = hamiltonian_path.get(j + 1);

            addEdgeToList( node1, node2 );
        }

        int i = n_nodes;
        while (i <= num_edges) {
            int node1 = rand.nextInt(n_nodes);
            int node2 = rand.nextInt(n_nodes);

            if( node1 == node2 ) continue;
            if( adjList[node1].getLinkedNodes().contains(node2) ) continue;

            addEdgeToList( node1, node2 );

            i++;
        }
    }

    /**
     * Creates a random unweighted graph with a maximum weight.
     * This operation is not supported in an unweighted graph.
     *
     * @param max_weight The maximum weight of the edges.
     */
    @Override
    public void createRandomGraph(int max_weight){
        throw new RuntimeException("Error createRandomGraph(int max_weight): Cannot create a Random Unweighted Graph with weights.\n");
    }
}

package graph;

import simulation.InputParameters;

import java.util.ArrayList;

/**
 * The IGraph interface represents a graph.
 * It defines methods for accessing nodes, adding edges, and creating random graphs.
 */
public interface IGraph {

    /**
     * Gets the node with the specified ID.
     *
     * @param id The ID of the node.
     * @return The node with the specified ID.
     */
    Node getNode(int id);

    /**
     * Gets an array of all nodes in the graph.
     *
     * @return An array of all nodes in the graph.
     */
    Node[] getNodeList();

    /**
     * Adds an edge between the specified nodes.
     *
     * @param n1 The ID of the first node.
     * @param n2 The ID of the second node.
     */
    void addEdgeToList(int n1, int n2);

    /**
     * Adds a weighted edge between the specified nodes.
     *
     * @param n1 The ID of the first node.
     * @param n2 The ID of the second node.
     * @param weight The weight of the edge.
     */
    void addEdgeToList(int n1, int n2, int weight);

    /**
     * Creates a random graph.
     */
    void createRandomGraph();

    /**
     * Creates a random graph with a maximum weight for the edges.
     *
     * @param max_weight The maximum weight of the edges.
     */
    void createRandomGraph(int max_weight);

    /**
     * Creates a graph based on the given input parameters.
     *
     * @param parameters The input parameters for creating the graph.
     */
    void createGivenGraph(InputParameters parameters);

    /**
     * Generates a random Hamiltonian path in the graph.
     *
     * @return An ArrayList containing the node IDs in the Hamiltonian path.
     */
    ArrayList<Integer> generateRandomHamiltonianPath();

}

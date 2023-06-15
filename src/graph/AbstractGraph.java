package graph;

import java.util.ArrayList;
import java.util.Collections;

/**
 * The AbstractGraph class provides a base implementation for common graph operations.
 * It serves as an abstract class that can be extended by concrete graph implementations.
 */
public abstract class AbstractGraph implements IGraph{
    protected int n_nodes;
    protected int max_edges;
    protected Node[] adjList;

    /**
     * Constructs an AbstractGraph object with the specified number of nodes.
     *
     * @param n_nodes The number of nodes in the graph.
     */
    public AbstractGraph(int n_nodes){
        this.n_nodes = n_nodes;
        this.max_edges = (n_nodes * (n_nodes - 1)) / 2;
        this.adjList = new Node[n_nodes];
        for(int l = 0; l < n_nodes; l++){
            this.adjList[l] = new Node(l);
        }
    }

    @Override
    public Node getNode(int id) {
        return this.adjList[ id ];
    }

    @Override
    public Node[] getNodeList() {
        return adjList;
    }

    @Override
    public void addEdgeToList(int n1, int n2) {

    }

    @Override
    public void addEdgeToList(int n1, int n2, double weight) {

    }

    /**
     * Generates a random Hamiltonian path in the graph.
     *
     * @return The randomly generated Hamiltonian path.
     */
    @Override
    public ArrayList<Integer> generateRandomHamiltonianPath() {
        ArrayList<Integer> hamiltonian_path = new ArrayList<>(n_nodes);

        for (int i = 0; i < n_nodes; i++)
            hamiltonian_path.add(i);

        Collections.shuffle(hamiltonian_path);

        return hamiltonian_path;
    }

    /**
     * Gets the maximum number of edges in the graph.
     *
     * @return The maximum number of edges.
     */
    public int getMaxEdges() {
        return max_edges;
    }

    /**
     * Gets the total number of nodes in the graph.
     *
     * @return The total number of nodes.
     */
    public int getTotalNodes() {
        return n_nodes;
    }
}

package aco;

import graph.Edge;
import graph.Node;
import simulation.Simulation;

import java.util.*;

/**
 * The Ant class represents an ant in the Ant Colony Optimization (ACO) algorithm.
 * Ants traverse the graph, constructing paths and making decisions based on pheromone levels and edge weights.
 */
public class Ant {
    private final Simulation simulation = Simulation.getSimulation();

    private final int id;
    private static int id_increments = 0;
    private Node current_node;
    private final ArrayList<Integer> path;
    private final LinkedList<Edge> path_edges;

    /**
     * Constructs an Ant object with the given starting node.
     *
     * @param node The starting node for the ant.
     */
    public Ant(Node node) {
        this.current_node = node;
        path = new ArrayList<>();
        path.add(current_node.getId());
        path_edges = new LinkedList<>();
        this.id = id_increments;
        id_increments++;
    }

    /**
     * Gets the ID of the ant.
     *
     * @return The ID of the ant.
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the edges in the path traversed by the ant.
     *
     * @return The edges in the ant's path.
     */
    public LinkedList<Edge> getPathEdges() {
        return path_edges;
    }

    /**
     * Sets the current node of the ant.
     *
     * @param node The current node to be set.
     */
    public void setCurrentNode(Node node) {
        current_node = node;
    }

    /**
     * Gets the current node of the ant.
     *
     * @return The current node of the ant.
     */
    public Node getCurrentNode() {
        return current_node;
    }

    /**
     * Adds a node ID to the ant's path.
     *
     * @param node_id The ID of the node to be added to the path.
     */
    public void addToPath(int node_id) {
        path.add(node_id);
    }

    /**
     * Adds an edge to the ant's path edges.
     *
     * @param edge The edge to be added to the path edges.
     */
    public void addToPathEdges(Edge edge) {
        path_edges.addLast(edge);
    }

    /**
     * Gets the path traversed by the ant.
     *
     * @return The path traversed by the ant.
     */
    public ArrayList<Integer> getPath() {
        return path;
    }

    /**
     * Gets the neighboring edges of the current node.
     *
     * @return The neighboring edges of the current node.
     */
    public List<Edge> getNeighEdges() {
        return current_node.getEdges();
    }

    /**
     * Calculates the total weight of the ant's path.
     *
     * @return The total weight of the ant's path.
     */
    public int getTotalPathWeight() {
        int sum = 0;
        for (Edge edge : path_edges) {
            sum += edge.getWeight();
        }
        return sum;
    }

     /**
     * Gets the non-visited neighboring edges of the current node.
     *
     * @return The non-visited neighboring edges of the current node.
     */
    public List<Edge> getNonVisitedNeighEdges(){
        List<Edge> non_visited_edges = new ArrayList<>();

        // For each neighboring edge, check if the destination node has been visited
        // If not, add the edge to the list of non-visited edges
        for (Edge edge : getNeighEdges()){
            if (  !path.contains( edge.getDestination()) ) {
                non_visited_edges.add(edge);
            }
        }

        return non_visited_edges;
    }

        /**
     * Gets the next chosen edge for the ant to traverse based on the probability calculation.
     * 
     * @return The next chosen edge for the ant.
     */
    public Edge getNextChosenEdge(){
        List<Edge> edges = getNonVisitedNeighEdges();

        // if every neighboring edge (node) has been visited
        // the list of edges to choose from will be every neighboring edge
        if( edges.size() == 0) edges = getNeighEdges();

        Edge next_edge = null;
        double normalize_const = 0.0;
        Pheromones pheromones = Pheromones.getPheromones();

        // Calculate the constant for normalization
        for (Edge edge : edges) {
            double edge_fav_outcome = edgeFavorableOutcomeProbability(
                    simulation.getAlpha(),
                    simulation.getBeta(),
                    pheromones.getPheromoneLevel(edge),
                    edge.getWeight()
            );
            normalize_const += edge_fav_outcome;
        }

        double p = Math.random();
        double cumulativeProbability = 0.0;

        // Choose an edge randomly while considering the probability of choosing each edge
        for (Edge edge : edges) {
            cumulativeProbability += edgeFavorableOutcomeProbability(
                    simulation.getAlpha(),
                    simulation.getBeta(),
                    pheromones.getPheromoneLevel(edge),
                    edge.getWeight()
            ) / normalize_const;
            if (p <= cumulativeProbability) {
                next_edge = edge;
                break;
            }
        }

        return next_edge;
    }

    /**
     * Calculates the probability of choosing a specific edge based on alpha, beta, pheromone level, and edge weight.
     * 
     * @param alpha The alpha parameter in the probability calculation.
     * @param beta The beta parameter in the probability calculation.
     * @param pheromone The pheromone level of the edge.
     * @param weight The weight of the edge.
     * @return The probability of choosing the specific edge.
     */
    private double edgeFavorableOutcomeProbability(double alpha, double beta, double pheromone, double weight){
        // Calculate the probability of choosing a specific edge
        return  (alpha + pheromone) / (beta + weight);
    }

    /**
     * Removes a cycle that is not Hamiltonian by removing nodes and edges included in the cycle.
     * 
     * @param cycle_start_index The index at which the cycle starts in the path.
     */
    public void removeCycle(int cycle_start_index){
        // Remove a cycle that is not Hamiltonian
        // Removes nodes and edges included in the cycle
        for (int i = path.size()-1 ; i > cycle_start_index; i--){
            path.remove(i);
            path_edges.removeLast();
        }
    }

    /**
     * Checks if the path of the ant is Hamiltonian, i.e., it visits all nodes exactly once.
     * 
     * @param tot_nodes The total number of nodes in the graph.
     * @return True if the path is Hamiltonian, False otherwise.
     */
    public boolean pathIsHamiltonian(int tot_nodes){
        // Put all array elements in a HashSet
        Set<Integer> s = new HashSet<>(path);

        // If all elements are distinct, the size of the HashSet should be the same as the array
        return (s.size() == tot_nodes);
    }

    /**
     * Resets the path of the ant by clearing the path and path edges.
     */
    public void resetPath(){
        path.clear();
        path_edges.clear();
    }
}

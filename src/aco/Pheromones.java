package aco;

import graph.Edge;
import java.util.ArrayList;
import java.util.Collections;

/**
 * The Pheromones class represents the pheromone levels on edges in the graph.
 * It allows adding and retrieving the pheromone levels for specific edges.
 */
public class Pheromones {
    private final ArrayList<Double> tunnels;
    private static Pheromones pheromones;

    /**
     * Constructs a Pheromones object with the specified maximum number of edges.
     *
     * @param max_edges The maximum number of edges in the graph.
     */
    private Pheromones(int max_edges){
        // 2 * max_edges because edges are unidirectional but each edge has a reverse edge that leads from
        // destination to origin
        tunnels = new ArrayList<>(Collections.nCopies(2 * max_edges, 0.0));
    }

    /**
     * Retrieves the singleton instance of the Pheromones class.
     *
     * @return The singleton instance of Pheromones.
     */
    public static Pheromones getPheromones(){
        return pheromones;
    }

    /**
     * Retrieves the singleton instance of the Pheromones class and initializes it with the specified maximum number of edges.
     *
     * @param max_edges The maximum number of edges in the graph.
     * @return The singleton instance of Pheromones.
     */
    public static Pheromones getPheromones(int max_edges){
        if(pheromones == null){
            pheromones = new Pheromones(max_edges);
        }
        return pheromones;
    }

    /**
     * Adds pheromones to the specified edge by updating its pheromone level.
     *
     * @param edge The edge to which pheromones should be added.
     * @param add_pheromones The amount of pheromones to add to the edge.
     */
    public void addPheromone(Edge edge, double add_pheromones){
        double updatedPheromoneLevel = getPheromoneLevel(edge) + add_pheromones;

        tunnels.set(edge.getId(), updatedPheromoneLevel);
        tunnels.set(edge.getReverseEdge().getId(), updatedPheromoneLevel);
    }

    /**
     * Sets the pheromone level of the specified edge.
     *
     * @param edge The edge for which to set the pheromone level.
     * @param pheromoneLevel The new pheromone level for the edge.
     */
    public void setPheromone(Edge edge, double pheromoneLevel){
        tunnels.set(edge.getId(), pheromoneLevel);
    }

    /**
     * Retrieves the pheromone level of the specified edge.
     *
     * @param edge The edge for which to retrieve the pheromone level.
     * @return The pheromone level of the edge.
     */
    public double getPheromoneLevel(Edge edge) {
        return tunnels.get(edge.getId());
    }
}

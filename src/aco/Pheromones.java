package aco;

import graph.Edge;
import graph.WeightedGraph;
import simulation.InputParameters;
import simulation.Simulation;

import java.util.ArrayList;
import java.util.Collections;

public class Pheromones {
    private ArrayList<Double> tunnels;
    private static Pheromones pheromones;
    private final WeightedGraph graph = WeightedGraph.getGraph();

    private Pheromones(int max_edges){
        System.out.println("max edges: " + max_edges);

        tunnels = new ArrayList<>(Collections.nCopies( 2*max_edges, 0.0));

    }
    public static Pheromones getPheromones(){
        return pheromones;
    }

    public static Pheromones getPheromones(int max_edges){
        if(pheromones == null){
            pheromones = new Pheromones(max_edges);
        } return pheromones;
    }

    public void addPheromone(Edge edge, double add_pheromones){
        System.out.println(edge);
        System.out.println("old pheromone level: " + getPheromoneLevel(edge));
        double updatedPheromoneLevel = getPheromoneLevel(edge) + add_pheromones;
        tunnels.set( edge.getId() , updatedPheromoneLevel);
        tunnels.set(edge.getReverseEdge().getId(), updatedPheromoneLevel);
        System.out.println("new added pheromone level: " + getPheromoneLevel(edge));
        System.out.println();
    }

    public void setPheromone(Edge edge, double pheromoneLevel){
        System.out.println("b4 evap pheromone level: " + getPheromoneLevel(edge));
        tunnels.set( edge.getId() , pheromoneLevel);
        System.out.println("after evap pheromone level: " + getPheromoneLevel(edge));
    }

    public double getPheromoneLevel(Edge edge) {
        //System.out.println(tunnels);
        return tunnels.get(edge.getId());
    }
}

package aco;

import graph.Edge;

import java.util.ArrayList;
import java.util.Collections;

public class Pheromones {
    private ArrayList<Double> tunnels;
    private static Pheromones pheromones;

    private Pheromones(int max_edges){
        tunnels = new ArrayList<>(Collections.nCopies( 2*max_edges, 0.0));

    }
    public static Pheromones getPheromones(){
        return pheromones;
    }

    public static Pheromones getPheromones(int max_edges){
        if(pheromones==null){
            pheromones = new Pheromones(max_edges);
        } return pheromones;
    }

    public void addPheromone(Edge edge, double add_pheromones){
        // procurar o equivalente
        tunnels.set( edge.getId() , getPheromone(edge) + add_pheromones);
    }

    public void setPheromone(Edge edge, double pheromoneLevel){
        tunnels.set( edge.getId() , pheromoneLevel);
    }

    public double getPheromone(Edge edge) {
        //System.out.println(tunnels);
        return tunnels.get(edge.getId());
    }
}

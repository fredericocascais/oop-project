package aco;

import graph.Edge;

import java.util.ArrayList;
import java.util.Collections;

public class Pheromones {
    private ArrayList<Double> tunnels;

    public Pheromones(int max_edges){
        tunnels = new ArrayList<>(Collections.nCopies( 2*max_edges, 0.0));

    }

    public void setPheromone(Edge edge, double add_pheromones){
        tunnels.set( edge.getId() , getPheromone(edge) + add_pheromones);
    }

    public double getPheromone(Edge edge) {
        //System.out.println(tunnels);
        return tunnels.get(edge.getId());
    }
}

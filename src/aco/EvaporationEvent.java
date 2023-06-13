package aco;

import graph.Edge;
import pec.Event;
import simulation.Simulation;

public class EvaporationEvent extends Event{
    private final Edge edge;

    Simulation simulation = Simulation.getSimulation();

    public EvaporationEvent(Edge edge){
        this.edge=edge;

        setEventType("evaporation");
        setEventTime(simulation.getEta());
    }

    public Object executeEvent(){
        Pheromones pheromones = Pheromones.getPheromones();
        double decrease = simulation.getRho();
        double edgePheromone = pheromones.getPheromone(edge);
        if(edgePheromone > decrease){
            edgePheromone-=decrease;
        }else{
            edgePheromone=0;
        }
        pheromones.setPheromone(edge, edgePheromone);
        addEventTimeToSimulation();     
        return edge;   
    }        
    public Edge getEdge(){
        return edge;
    }

}

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

    public void executeEvent(){
        Pheromones pheromones = Pheromones.getPheromones();
        double decrease = simulation.getRho();
        double edgePheromone = pheromones.getPheromoneLevel(edge);
        if (edgePheromone <= 0) return;
        if(edgePheromone > decrease){
            edgePheromone-=decrease;
        }else{
            edgePheromone=0;
        }
        pheromones.setPheromone(edge, edgePheromone);
        pheromones.setPheromone(edge.getReverseEdge(), edgePheromone);
        simulation.addNewEvent(  new EvaporationEvent(edge) );
        addEventTimeToSimulation();     
    }


}

package aco;

import graph.Edge;
import pec.Event;
import simulation.Simulation;

/**
 * The EvaporationEvent class represents an event where the pheromone level on an edge evaporates.
 * It handles the execution of the event and updates the pheromone level accordingly.
 */
public class EvaporationEvent extends Event{
    private final Edge edge;

    Simulation simulation = Simulation.getSimulation();

    /**
     * Constructs an EvaporationEvent object for the specified edge.
     *
     * @param edge The edge on which the pheromone level will evaporate.
     */
    public EvaporationEvent(Edge edge){
        this.edge = edge;

        setEventType("evaporation");
        setEventTime(simulation.getEta());
    }

    /**
     * Executes the evaporation event by reducing the pheromone level on the edge,
     * scheduling a new evaporation event, and updating the simulation statistics.
     */
    public void executeEvent(){
        Pheromones pheromones = Pheromones.getPheromones();
        double decrease = simulation.getRho();
        double edgePheromone = pheromones.getPheromoneLevel(edge);
        if (edgePheromone <= 0) return;
        if(edgePheromone > decrease){
            edgePheromone -= decrease;
        } else {
            edgePheromone = 0;
        }
        pheromones.setPheromone(edge, edgePheromone);
        pheromones.setPheromone(edge.getReverseEdge(), edgePheromone);
        simulation.addNewEvent(new EvaporationEvent(edge));
        simulation.increaseTotalEvaporations();
        addEventTimeToSimulation();
    }

    /**
     * Gets the edge associated with this evaporation event.
     *
     * @return The edge associated with this event.
     */
    public Edge getEdge() {
        return edge;
    }
}

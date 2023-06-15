package aco;

import graph.Edge;
import graph.HamiltoneanCycle;
import graph.Node;
import graph.WeightedGraph;
import pec.Event;
import simulation.Simulation;

import java.util.ArrayList;


public class AntMoveEvent extends Event{
    private final Simulation simulation = Simulation.getSimulation();
    private final WeightedGraph graph = WeightedGraph.getGraph();
    private final Ant ant;
    private final ArrayList<Integer> current_ant_path;
    private final int nest_node_id;
    private final Edge next_edge;

    public AntMoveEvent( Ant ant, Edge next_edge){
        this.ant = ant;
        this.nest_node_id = simulation.getNestNode() - 1;
        this.current_ant_path = ant.getPath();
        this.next_edge = next_edge;
        setEventType("ant_move");
        setEventTime(simulation.getDelta() * next_edge.getWeight());
    }

    public Ant getAnt() {
        return ant;
    }

    public void executeEvent(){
        int next_node_id = next_edge.getDestination();

        // If path contains the next node's id means that
        // all the neighbouring edges have all been visited so a
        // node has been randomly chosen in a uniform distribution
        if( current_ant_path.contains( next_node_id ) ){

            // If the next node is the nest node and the path length is the same as the total number of node
            // we can assume that it has gone through all the nodes and a hamiltonian path has been completed,
            // so we add only the edge that leads to the nest node
            if ( next_node_id == nest_node_id && current_ant_path.size() == graph.getTotalNodes()) {
                ant.addToPathEdges(next_edge);
                ant.setCurrentNode( graph.getNode(next_node_id) );

                // Add pheromones to the edges that were transversed to perform a Hamiltonean Cycle
                // Create Evaporation Events for each edge that were transversed to perform a hamiltonean cycle
                // Add the Hamiltonean Cycle to the list of Halmiltonean Cycles found
                addPheromonesToPathEdges();
                createEvaporationEvents();
                addNewHamiltoneanCycle();

                // Reset the Ant's path to initial state
                ant.resetPath();
                ant.addToPath(nest_node_id);
            }
            // If it's the case above means that a cycle has been created which means
            // we need to eliminate it by starting over the index/node where the cycle begins
            else {
                int start_cycle_index = current_ant_path.indexOf(next_node_id);
                ant.removeCycle( start_cycle_index );
                ant.setCurrentNode( graph.getNode(next_node_id) );
            }

        // If the next node is not in the path then it means a non visited node has been chosen
        } else {
            ant.addToPathEdges( next_edge );
            ant.addToPath( next_node_id );
            ant.setCurrentNode( getNode(next_node_id) );
        }

        simulation.increaseTotalMoves();
        addEventTimeToSimulation();
        // Choose a new node to visit and add new Move Event for this Ant
        Edge next_chosen_edge = ant.getNextChosenEdge();
        simulation.addNewEvent( new AntMoveEvent(ant, next_chosen_edge) );
    }

    private void addPheromonesToPathEdges(){
        Pheromones pheromones = Pheromones.getPheromones();
        double totalWeight = graph.getTotalWeight();
        double gamma = simulation.getGamma();
        // Mathematical formula to increment the level of pheromones of the Edge
        double pheromoneIncrease = gamma * totalWeight / ant.getTotalPathWeight();

        for (Edge edge : ant.getPathEdges()){
            pheromones.addPheromone(edge, pheromoneIncrease);
        }
    }


    private Node getNode(int node_id){
        return graph.getNode(node_id);
    }

    private void addNewHamiltoneanCycle(){
        ArrayList<Integer> hamiltoneanPath = new ArrayList<>(ant.getPath());
        HamiltoneanCycle hamiltoneanCycle = new HamiltoneanCycle(ant.getTotalPathWeight(), hamiltoneanPath);
        simulation.addHamiltoneanCycle(hamiltoneanCycle);
    }


    public void createEvaporationEvents(){
        for ( Edge edge : ant.getPathEdges()){
            simulation.addNewEvent( new EvaporationEvent(edge));
        }
    }



}

package aco;

import graph.Edge;
import graph.HamiltonianCycle;
import graph.Node;
import graph.WeightedGraph;
import pec.Event;
import simulation.Simulation;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * The AntMoveEvent class represents an event where an ant moves along an edge in the graph.
 * It handles the execution of the event and updates the ant's path and other relevant information.
 */
public class AntMoveEvent extends Event{
    private final Simulation simulation = Simulation.getSimulation();
    private final WeightedGraph graph = WeightedGraph.getGraph();
    private final Ant ant;
    private final ArrayList<Integer> current_ant_path;
    private final int nest_node_id;
    private final Edge next_edge;

    /**
     * Constructs an AntMoveEvent object with the given ant and next edge.
     *
     * @param ant The ant that is moving.
     * @param next_edge The edge along which the ant is moving.
     */
    public AntMoveEvent( Ant ant, Edge next_edge){
        this.ant = ant;
        this.nest_node_id = simulation.getNestNode() - 1;
        this.current_ant_path = ant.getPath();
        this.next_edge = next_edge;
        setEventType("ant_move");
        setEventTime(simulation.getDelta() * next_edge.getWeight());
    }


    /**
     * Executes the ant move event by updating the ant's path, checking for cycles or completion of Hamiltonian path,
     * adding pheromones, creating evaporation events, and scheduling the next move event.
     */
    public void executeEvent(){
        int next_node_id = next_edge.getDestination();

        // If path contains the next node's id means that
        // all the neighboring edges have all been visited, so a
        // node has been randomly chosen in a uniform distribution
        if( current_ant_path.contains( next_node_id ) ){

            // If the next node is the nest node and the path length is the same as the total number of nodes,
            // we can assume that it has gone through all the nodes and a Hamiltonian path has been completed,
            // so we add only the edge that leads to the nest node
            if ( next_node_id == nest_node_id && current_ant_path.size() == graph.getTotalNodes()) {
                ant.addToPathEdges(next_edge);
                ant.setCurrentNode( graph.getNode(nest_node_id) );

                // Create Evaporation Events for each edge that were traversed to perform a Hamiltonian Cycle
                // Add pheromones to the edges that were traversed to perform a Hamiltonian Cycle
                // Add the Hamiltonian Cycle to the list of Hamiltonian Cycles found
                createEvaporationEvents();
                addPheromonesToPathEdges();
                addNewHamiltonianCycle();

                // Reset the Ant's path to the initial state
                ant.resetPath();
                ant.addToPath(nest_node_id);
            }
            // If it's the case above, it means that a cycle has been created, which means
            // we need to eliminate it by starting over the index/node where the cycle begins
            else {
                int start_cycle_index = current_ant_path.indexOf(next_node_id);
                ant.removeCycle( start_cycle_index );
                ant.setCurrentNode( graph.getNode(next_node_id) );
            }

        // If the next node is not in the path, it means a non-visited node has been chosen
        } else {
            ant.addToPathEdges( next_edge );
            ant.addToPath( next_node_id );
            ant.setCurrentNode( getNode(next_node_id) );
        }

        simulation.increaseTotalMoves();
        addEventTimeToSimulation();
        // Choose a new node to visit and add a new Move Event for this Ant
        Edge next_chosen_edge = ant.getNextChosenEdge();
        simulation.addNewEvent( new AntMoveEvent(ant, next_chosen_edge) );
    }

        /**
     * Adds pheromones to the edges in the ant's path.
     * The pheromone level is incremented based on a mathematical formula.
     */
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

    /**
     * Retrieves the node with the given ID from the graph.
     *
     * @param node_id The ID of the node to retrieve.
     * @return The node with the specified ID.
     */
    private Node getNode(int node_id){
        return graph.getNode(node_id);
    }

    /**
     * Adds a new Hamiltonian cycle to the simulation based on the ant's path.
     * The Hamiltonian cycle includes the total weight of the path and the node IDs.
     */
    private void addNewHamiltonianCycle(){
        ArrayList<Integer> hamiltonianPath = new ArrayList<>(ant.getPath());
        HamiltonianCycle hamiltonianCycle = new HamiltonianCycle(ant.getTotalPathWeight(), hamiltonianPath);
        simulation.addHamiltonianCycle(hamiltonianCycle);
    }

    /**
     * Creates evaporation events for the edges in the ant's path if they do not already exist in the event list.
     * Evaporation events are responsible for reducing the pheromone level of the edges over time.
     */
    public void createEvaporationEvents(){
        Pheromones pheromones = simulation.getPheromones();
        LinkedList<Edge> path_edges = new LinkedList<>( ant.getPathEdges() );

        for ( Edge edge : path_edges ){
            if(pheromones.getPheromoneLevel(edge) <= 0){
                simulation.addNewEvent( new EvaporationEvent(edge));
            }
        }
    }
}






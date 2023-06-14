package aco;

import graph.Edge;
import graph.HamiltoneanCycle;
import graph.Node;
import graph.WeightedGraph;
import pec.Event;
import pec.PEC;
import simulation.Simulation;

import java.util.ArrayList;


public class AntMoveEvent extends Event{
    private final Simulation simulation = Simulation.getSimulation();
    private final WeightedGraph graph = WeightedGraph.getGraph();
    private final Ant ant;
    private ArrayList<Integer> current_ant_path;
    private final int nest_node;
    private final Edge next_edge;

    public AntMoveEvent( Ant ant, Edge next_edge){
        this.ant = ant;
        this.nest_node = simulation.getNestNode();
        this.current_ant_path = ant.getPath();
        this.next_edge = next_edge;
        setEventType("ant_move");
        setEventTime(simulation.getDelta() * next_edge.getWeight());
    }

    public Ant getAnt() {
        return ant;
    }

    public Object executeEvent(){
        int next_node_id = next_edge.getDestination();

        // If path contains the next node's id means that
        // all the neighbouring edges have all been visited so a
        // node has been randomly chosen in a uniform distribution
        if( current_ant_path.contains( next_node_id ) ){

            // If the next node is the nest node and the path length is the same as the total number of node
            // we can assume that it has gone through all the nodes and a hamiltonian path has been completed,
            // so we add only the edge that leads to the nest node
            if ( next_node_id == nest_node && current_ant_path.size() == graph.get_total_nodes()) {
                ant.addToPathEdges(next_edge);
                ant.setCurrentNode( graph.getNode(next_node_id) );
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
            if( !current_ant_path.contains( nest_node ) ){
                ant.addToPath( nest_node );
            }
            ant.addToPath( next_node_id );
            ant.setCurrentNode( getNode(next_node_id) );
        }

        // edge id
        // 2 edges -> A a B e B a A
        // 1a vai ter id Par, 2a vai ter id Impar
        // next_edge ver qual o equivalente -> se for par o euqivalente vai ser com id + 1
        // se for impar o equivalente vai ser id - 1
        //
        // check hamiltonean - Joel
        // add hamilltonean - Joel
        //
        // TODO create evap Events - Joel
        // TODO add pheromones to edges - mariana
        

        // Add new Move Event for this Ant
        PEC.getPEC().addEvent(new AntMoveEvent(ant, next_edge));
    }

    private void addPheromone(double cycleWeight){
        Pheromones pheromones = Pheromones.getPheromones();
        double totalWeight = graph.getTotalWeight();
        double gama = simulation.getGamma();
        double pheromoneIncrease = gama*totalWeight/cycleWeight;

        pheromones.addPheromone(next_edge, pheromoneIncrease);
    }

    private Node getNode(int node_id){
        return graph.getNode(node_id);
    }

    private void addHamiltoneanCycle(){
        ArrayList<Integer> hamiltoneanPath = ant.getPath();
        HamiltoneanCycle hamiltoneanCycle = new HamiltoneanCycle();
        simulation.addHamiltoneanCycle(hamiltoneanCycle);
        ant.resetPath();
    }

    private Boolean checkIfHamiltoneanCycle(){
        int tot_nodes = graph.getTotalNodes();
        return (ant.getPathEdges().size() >= tot_nodes && ant.pathIsHamiltonean(tot_nodes));
    }



}

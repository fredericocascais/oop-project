package aco;

import graph.Edge;
import graph.Graph;
import graph.Node;
import graph.WeightedGraph;
import pec.Event;

import java.util.List;

public class AntMoveEvent extends Event{

    private final Ant ant;

    private final Edge next_edge;

    private final WeightedGraph graph;


    public AntMoveEvent( double time, WeightedGraph graph, Ant ant, Edge next_edge, double delta){

        this.ant = ant;
        setEventType("ant_move");
        this.next_edge = next_edge;
        this.graph = graph;

        setEventTime(time, delta * next_edge.getWeight());
    }

    public Ant getAnt() {
        return ant;
    }

    public Object executeEvent(){
        System.out.println("\nExecuting Ant Mov Event");
        System.out.println("ant id: " + ant.getId());
        int next_node_id = next_edge.getDestination();
        System.out.println("edge traversed: "+ next_edge);
        System.out.println("destination node id: "+ next_node_id);

        // If path contains the next node's id means that
        // all the neighbouring edges have all been visited so a
        // node has been randomly chosen in a uniform distribution
        if( ant.getPath().contains( next_node_id ) ){
            //System.out.println("Next node already in path");

            // If the next node is the nest node and the path length is the same as the total number of node
            // we can assume that it has gone through all the nodes and a hamiltonian path has been completed,
            // so we add only the edge that leads to the nest node
            if ( next_node_id == ant.getNestNode() && ant.getPath().size() == graph.get_total_nodes()) {
                ant.addToPathEdges(next_edge);
                ant.setCurrentNode(graph.getNode(next_node_id));
                System.out.println("next node id: "+  next_node_id);
                System.out.println("nest node id: " + ant.getNestNode());
                //System.out.println("HAMILTOJN");
                System.out.println("Cycle: "+ant.getPath());
                //System.exit(0);
            }
            // If it's the case above means that a cycle has been created which means
            // we need to eliminate it by starting over the index/node where the cycle begins
            else {
                //System.out.println("Removing, next");
                int start_cycle_index = ant.getPath().indexOf(next_node_id);
                ant.removeCycle( start_cycle_index );
                ant.setCurrentNode( graph.getNode(next_node_id) );
            }
        // If the next node is not in the path then it means a non visited node has been chosen
        } else {
            ant.addToPathEdges( next_edge );
            if(!ant.getPath().contains( ant.getNestNode())){
                ant.addToPath( ant.getNestNode() );
            }
            ant.addToPath( next_node_id );
            ant.setCurrentNode( graph.getNode(next_node_id) );
            //ant.setVisited(next_node_id);
        }
        //System.out.println("Current node: "+ant.getCurrentNode());
        System.out.println("Path edges: "+ant.getPathEdges());
        System.out.println("Path nodes: "+ant.getPath());
        return ant;
    }



}

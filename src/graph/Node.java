package graph;

import java.util.ArrayList;
import java.util.List;

/**
 * The Node class represents a node in a graph.
 * It contains information such as the node ID, the edges connected to the node, and the linked nodes.
 */
public class Node {
    /**
     * The ID of the node.
     */
    private final int id;

    /**
     * The list of edges connected to the node.
     */
    private final List<Edge> edges = new ArrayList<>();

    /**
     * The list of linked node IDs.
     */
    private final List<Integer> linkedNodes = new ArrayList<>();

    /**
     * Constructs a Node object with the specified ID.
     *
     * @param id The ID of the node.
     */
    public Node(int id){
        this.id = id;
    }

    /**
     * Adds an unweighted edge to another node in the graph.
     *
     * @param dest The ID of the destination node.
     */
    public void addEdgeToNode( int dest ){
        if( linkedNodes.contains( dest ) ){
            return;
        }
        linkedNodes.add(dest);
        this.edges.add( new Edge( dest , 0) );
    }

    /**
     * Adds a weighted edge to another node in the graph.
     *
     * @param dest The ID of the destination node.
     * @param weight The weight of the edge.
     */
    public void addEdgeToNode( int dest, double weight ){

        if( linkedNodes.contains( dest ) ){
            return;
        }
        linkedNodes.add(dest);
        this.edges.add( new Edge( dest, weight) );

    }

    /**
     * Gets the list of edges connected to the node.
     *
     * @return The list of edges connected to the node.
     */
    public List<Edge> getEdges() {
        return edges;
    }

    /**
     * Gets the edge with the specified ID.
     *
     * @param edgeId The ID of the edge.
     * @return The edge with the specified ID, or null if not found.
     */
    public Edge getEdgeById(int edgeId) {
        for (Edge edge : edges) {
            if (edge.getId() == edgeId) {
                return edge;
            }
        }
        return null;
    }

    /**
     * Gets the list of IDs of linked nodes.
     *
     * @return The list of IDs of linked nodes.
     */
    public List<Integer> getLinkedNodes() {
        return linkedNodes;
    }

    /**
     * Gets the ID of the node.
     *
     * @return The ID of the node.
     */
    public int getId() {
        return id;
    }

    /**
     * Returns a string representation of the node.
     *
     * @return A string representation of the node.
     */
    public String toString(){
        return "Node "+ id + " with "+ edges.size() + " edges";
    }
}

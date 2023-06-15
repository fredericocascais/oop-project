package graph;

/**
 * The IEdge interface represents an edge in a graph.
 * It defines the methods to get the destination and ID of the edge.
 */
public interface IEdge {
    /**
     * Gets the destination node ID of the edge.
     *
     * @return The ID of the destination node.
     */
    public int getDestination();

    /**
     * Gets the ID of the edge.
     *
     * @return The ID of the edge.
     */
    public int getId();
}


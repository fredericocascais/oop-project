package graph;

/**
 * The Edge class represents an edge in a graph.
 * It contains information such as the destination node, weight, and ID of the edge.
 */
public class Edge implements IEdge{
    private final int destination;
    private final double weight;
    private final int id;
    private static int count = 0;

    /**
     * Constructs an Edge object with the specified destination and weight.
     *
     * @param dest The ID of the destination node.
     * @param weight The weight of the edge.
     */
    public Edge(int dest, double weight){
        this.destination = dest;
        this.id = count;
        this.weight = weight;
        count++;
    }

    /**
     * Gets the destination node ID of the edge.
     *
     * @return The ID of the destination node.
     */
    public int getDestination() {
        return destination;
    }

    /**
     * Gets the ID of the edge.
     *
     * @return The ID of the edge.
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the weight of the edge.
     *
     * @return The weight of the edge.
     */
    public double getWeight() {
        return weight;
    }

    /**
     * Gets the reverse edge of this edge.
     *
     * @return The reverse edge of this edge.
     */
    public Edge getReverseEdge(){
        int reverseEdgeId;
        if( (id % 2) == 0){
            reverseEdgeId = id + 1;
        }else{
            reverseEdgeId = id - 1;
        }
        WeightedGraph graph = WeightedGraph.getGraph();
        Node destNode = graph.getNode(destination);
        return destNode.getEdgeById(reverseEdgeId);
    }

    /**
     * Returns a string representation of the edge.
     *
     * @return A string representation of the edge.
     */
    @Override
    public String toString(){
        return "edge id: " + id + " with weight = " + weight;
    }
}

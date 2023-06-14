package graph;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private final int id;
    private List<Edge> edges = new ArrayList<>();
    private List<Integer> linkedNodes = new ArrayList<>();

    public Node(int id){
        this.id = id;
    }

    public void addEdgeToNode( int dest ){
        if( linkedNodes.contains( dest ) ){
            return;
        }
        linkedNodes.add(dest);
        this.edges.add( new Edge( dest , 0) );
    }

    public void addEdgeToNode( int dest, double weight ){

        if( linkedNodes.contains( dest ) ){
            return;
        }
        linkedNodes.add(dest);
        this.edges.add( new Edge( dest, weight) );

    }

    public List<Edge> getEdges() {
        return edges;
    }

    public Edge getEdgeById(int edgeId) {
        for (Edge edge : edges) {
            if (edge.getId() == edgeId) {
                return edge;
            }
        }
        return null;
    }

    public List<Integer> getLinkedNodes() {
        return linkedNodes;
    }

    public int getId() {
        return id;
    }

    public String toString(){

        return "Node "+ id + " with "+ edges.size() + " edges";
    }
}



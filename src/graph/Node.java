package graph;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private final int id;
    private List<Edge> edges = new ArrayList<>();
    private List<Integer> linked = new ArrayList<>();

    public Node(int id){
        this.id = id;
    }

    public void addEdgeToNode( int dest ){
        if( linked.contains( dest ) ){
            return;
        }
        linked.add(dest);
        this.edges.add( new Edge( dest , 0) );
    }

    public void addEdgeToNode( int dest, double weight ){

        if( linked.contains( dest ) ){
            return;
        }
        linked.add(dest);
        this.edges.add( new Edge( dest, weight) );

    }

    public List<Edge> getEdges() {
        return edges;
    }

    public List<Integer> getLinked() {
        return linked;
    }

    public int getId() {
        return id;
    }

    public String toString(){

        return "Node "+ id + " with "+ edges.size() + " edges";
    }
}



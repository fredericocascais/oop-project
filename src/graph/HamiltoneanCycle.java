package graph;
import java.util.ArrayList;
import java.util.Stack;

public class HamiltoneanCycle {
    private int totalWeight;
    private ArrayList<Integer> path;

    private ArrayList<Edge> edges;

    public HamiltoneanCycle(int totalWeight, ArrayList<Integer> path, ArrayList<Edge> edges){
        this.path = path;
        this.totalWeight = totalWeight;
        this.edges = edges;
    }

    @Override
    public String toString(){
        String pathString = path.toString().replace('[','{');
        pathString = pathString.replace(']','}');

        return pathString + ":" + totalWeight;
    }
}

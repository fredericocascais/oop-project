package graph;

import java.util.ArrayList;
import java.util.Collections;

public abstract class AbstractGraph implements IGraph{
    protected int n_nodes;
    protected int max_edges;
    protected Node[] adjList;

    public AbstractGraph(int n_nodes){
        this.n_nodes = n_nodes;
        this.max_edges = (n_nodes * (n_nodes - 1)) / 2;
        this.adjList = new Node[n_nodes];
        for(int l = 0; l < n_nodes; l++){
            this.adjList[l] = new Node(l+1);
        }
    }

    @Override
    public Node getNode(int id) {
        return this.adjList[ id ];
    }

    @Override
    public Node[] getNodeList() {
        return adjList;
    }

    @Override
    public void addEdgeToList(int n1, int n2) {

    }

    @Override
    public void addEdgeToList(int n1, int n2, double weight) {

    }


    //public abstract void createGivenGraph(int tot_nodes, String[] line, WeightedGraph graph);

    @Override
    public void createRandomGraph() {

    }

    @Override
    public void createGivenGraph(int tot_nodes, String[] line, WeightedGraph graph) {

    }

    @Override
    public void createRandomGraph(int max_weight) {

    }


    @Override
    public ArrayList<Integer> generateRandomHamiltoneanPath() {
        ArrayList<Integer> hamiltonean_path = new ArrayList<>(n_nodes);

        for (int i = 0; i < n_nodes; i++)
            hamiltonean_path.add(i);

        Collections.shuffle(hamiltonean_path);

        return hamiltonean_path;
    }

    public int getMaxEdges() {
        return max_edges;
    }

    public int get_total_nodes() {
        return n_nodes;
    }
}

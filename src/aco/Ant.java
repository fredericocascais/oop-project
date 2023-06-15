package aco;

import graph.Edge;
import graph.Node;
import simulation.Simulation;

import java.util.*;

public class Ant {
    private final Simulation simulation = Simulation.getSimulation();

    private final int id;
    private static int id_increments = 0;
    private Node current_node;
    private final ArrayList<Integer> path;
    private final LinkedList<Edge> path_edges;


    public Ant(Node node){
        this.current_node = node;
        path = new ArrayList<>();
        path.add(current_node.getId());
        path_edges = new LinkedList<>();
        this.id=id_increments;
        id_increments++;
    }

    public int getId() {
        return id;
    }
    public LinkedList<Edge> getPathEdges() {
        return path_edges;
    }

    public void setCurrentNode(Node node){
        current_node = node;
    }

    public Node getCurrentNode() {
        return current_node;
    }

    public void addToPath(int node_id){
        path.add(node_id);
    }

    public void addToPathEdges(Edge edge) { path_edges.addLast(edge); }

    public ArrayList<Integer> getPath() {
        return path;
    }




    public List<Edge> getNeighEdges(){
        return current_node.getEdges();
    }

    public int getTotalPathWeight(){
        int sum = 0;
        for (Edge edge : path_edges){
            sum += edge.getWeight();
        }

        return sum;
    }

    public List<Edge> getNonVisitedNeighEdges(){
        List<Edge> non_visited_edges = new ArrayList<>();

        // For each neighbouring edge check if the destination node has been visite
        // if not then add to the list of non visited edges
        for ( Edge edge : getNeighEdges()){
            if (  !path.contains( edge.getDestination()) ) {
                non_visited_edges.add(edge);
            }
        }

        return non_visited_edges;
    }

    public Edge getNextChosenEdge(){
        List<Edge> edges = getNonVisitedNeighEdges();

        // if every neighbouring edge (node) has been visited
        // list of edges to choose is going to be every neighbouring edges
        if( edges.size() == 0) edges = getNeighEdges();

        Edge next_edge = null;
        double normalize_const = 0.0;
        Pheromones pheromones = Pheromones.getPheromones();

        // Calculate the constant for normalization
        for (Edge edge : edges) {
            double edge_fav_outcome = edgeFavorableOutcomeProbability(
                    simulation.getAlpha(),
                    simulation.getBeta(),
                    pheromones.getPheromoneLevel(edge),
                    edge.getWeight()
            );
            normalize_const += edge_fav_outcome;
        }



        double p = Math.random();
        double cumulativeProbability = 0.0;

        // Choose randomly an edge while taking consideration of the probability
        // of choosing each edge randomly
        for (Edge edge : edges) {
            cumulativeProbability += edgeFavorableOutcomeProbability(
                    simulation.getAlpha(),
                    simulation.getBeta(),
                    pheromones.getPheromoneLevel(edge),
                    edge.getWeight()
            ) / normalize_const;
            if (p <= cumulativeProbability) {
                next_edge = edge;
                break;
            }
        }

        return next_edge;
    }

    private static double edgeFavorableOutcomeProbability(double alpha, double beta, double pheromone, double weight){
        // Calculate the probability of choosing especific edge
        return  (alpha + pheromone) / (beta + weight);
    }

    public void removeCycle(int cycle_start_index){
        // Remove a cycle that is not hamiltonean
        // Removes nodes and edges included in the cycle
        for (int i = path.size()-1 ; i > cycle_start_index; i--){
            path.remove(i);
            path_edges.removeLast();
        }
    }

    public boolean pathIsHamiltonean(int tot_nodes){
        // Put all array elements in a HashSet
        Set<Integer> s =
                new HashSet<>(path);

        // If all elements are distinct, size of
        // HashSet should be same array.
        return (s.size() == tot_nodes);
    }

    public void resetPath(){
        path.clear();
        path_edges.clear();
    }
}

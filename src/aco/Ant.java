package aco;

import graph.Edge;
import graph.Node;
import simulation.Simulation;

import java.util.*;

public class Ant {
    private final Simulation simulation = Simulation.getSimulation();

    private int id;
    private static int id_increments = 0;
    private Node current_node;
    private final ArrayList<Integer> path;
    private LinkedList<Edge> path_edges;


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

        for ( Edge edge : getNeighEdges()){
            if (  !path.contains( edge.getDestination()) ) {
                non_visited_edges.add(edge);
            }
        }

        return non_visited_edges;
    }

    public Edge getNextChosenEdge(){
        List<Edge> edges = getNonVisitedNeighEdges();
        System.out.println("\t\tNon Visited Edges: "+edges);
        if( edges.size() == 0) edges = getNeighEdges();

        Edge next_edge = null;
        double norm_const = 0.0;

        Pheromones pheromones = Pheromones.getPheromones();


        for (Edge edge : edges) {
            double edge_fav_outcome = edgeFavorableOutcomeProbability(
                    simulation.getAlpha(),
                    simulation.getBeta(),
                    pheromones.getPheromoneLevel(edge),
                    edge.getWeight()
            );
            norm_const += edge_fav_outcome;
        }



        double p = Math.random();
        double cumulativeProbability = 0.0;

        for (Edge edge : edges) {

            cumulativeProbability += edgeFavorableOutcomeProbability(
                    simulation.getAlpha(),
                    simulation.getBeta(),
                    pheromones.getPheromoneLevel(edge),
                    edge.getWeight()
            ) / norm_const;
            if (p <= cumulativeProbability) {
                next_edge = edge;
                break;
            }
        }
        System.out.println("\t\tNext " + next_edge);
        return next_edge;

    }

    private static double edgeFavorableOutcomeProbability(double alpha, double beta, double pheromone, double weight){
        return  (alpha + pheromone) / (beta + weight);
    }

    public void removeCycle(int cycle_start_index){
        for (int i = path.size()-1 ; i > cycle_start_index; i--){
            path.remove(i);
            path_edges.removeLast();
            System.out.println("\t\tRemoving from index: "+i);
        }

    }

    public boolean pathIsHamiltonean(int tot_nodes){
        // Put all array elements in a HashSet
        Set<Integer> s =
                new HashSet<Integer>(path);

        // If all elements are distinct, size of
        // HashSet should be same array.
        return (s.size() == tot_nodes);
    }

    public void resetPath(){
        path.clear();
        path_edges.clear();
    }
}

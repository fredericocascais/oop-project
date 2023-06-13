package graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class Graph extends AbstractGraph{

    public Graph(int n_nodes) {
        super(n_nodes);
    }

    @Override
    public void addEdgeToList(int n1, int n2 ){
        this.adjList[ n1 ].addEdgeToNode( n2 );
        this.adjList[ n2 ].addEdgeToNode( n1 );
    }

    public void addEdgeToList(int n1, int n2, double weight){
        throw new RuntimeException("Error addEdgeToList(int n1, int n2, double weight): Cannot add a Weighted Edge to an Unweighted Graph.\n");
    }

    @Override
    public void createGivenGraph(int tot_nodes, String[] line, WeightedGraph graph) {

    }

    @Override
    public void createRandomGraph(){

        Random rand = new Random();

        int num_edges = rand.nextInt(max_edges - n_nodes) + n_nodes;
        ArrayList<Integer> hamiltonean_path = generateRandomHamiltoneanPath();

        for (int j = 0; j < hamiltonean_path.size(); j++) {
            if( j + 1 == hamiltonean_path.size()) break;
            int node1 = hamiltonean_path.get(j);
            int node2 = hamiltonean_path.get(j + 1);


            addEdgeToList( node1, node2 );
        }

        int i = n_nodes;
        while (i <= num_edges) {
            int node1 = rand.nextInt(n_nodes);
            int node2 = rand.nextInt(n_nodes);

            if( node1 == node2 ) continue;
            if( adjList[node1].getLinked().contains(node2) ) continue;


            addEdgeToList( node1, node2 );

            i++;
        }
    }

    @Override
    public void createRandomGraph(int max_weight){
        throw new RuntimeException("Error createRandomGraph(int max_weight): Cannot create a Random Unweighted Graph with weights.\n");
    }


}

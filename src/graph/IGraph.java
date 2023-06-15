package graph;

import simulation.InputParameters;

import java.util.ArrayList;

public interface IGraph {

     Node getNode(int id);
     Node[] getNodeList();
     void addEdgeToList(int n1, int n2 );
     void addEdgeToList(int n1, int n2 , double weight);
     void createRandomGraph();
     void createRandomGraph(int max_weight);void createGivenGraph(InputParameters parameters);

    ArrayList<Integer> generateRandomHamiltoneanPath();

}

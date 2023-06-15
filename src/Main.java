import graph.WeightedGraph;
import simulation.InputParameters;
import simulation.Simulation;
import java.io.FileNotFoundException;


public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        if(args.length<2){
            System.out.println("Arguments Error");
            System.exit(1);
        }

        InputParameters parameters = new InputParameters(args);
        WeightedGraph graph = WeightedGraph.getGraph( parameters.getTotalNodes() );

        if( args[0].equals("-r") ) graph.createRandomGraph( parameters.getMaxWeight() );
        else if ( args[0].equals("-f") ) graph.createGivenGraph(parameters);

        Simulation simulation = Simulation.getSimulation(parameters, graph);
        simulation.initSimulation();
        simulation.runSimulation();


    }


}
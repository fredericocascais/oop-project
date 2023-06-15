package simulation;
import graph.WeightedGraph;
import logger.Logger;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * The Main class is the entry point of the program.
 * It parses command line arguments, initializes objects, and runs the simulation.
 */
public class Main {

    /**
     * The main method is the entry point of the program.
     * It parses command line arguments, initializes objects, and runs the simulation.
     *
     * @param args The command line arguments.
     * @throws FileNotFoundException If the output file is not found.
     */
    public static void main(String[] args) throws FileNotFoundException {
        // If there's less than 2 arguments the execution stops
        if(args.length<2){
            System.out.println("Arguments Error");
            System.exit(1);
        }

        // Prepare logger to output an output file of what was printed in the terminal
        FileOutputStream file = new FileOutputStream("test.txt");
        Logger logger = new Logger(file, System.out);
        System.setOut(logger);

        // Initialize necessary objects
        InputParameters parameters = new InputParameters(args);
        WeightedGraph graph = WeightedGraph.getGraph( parameters.getTotalNodes() );

        // Create graph depending on initial arguments
        if( args[0].equals("-r") ) graph.createRandomGraph( parameters.getMaxWeight() );
        else if ( args[0].equals("-f") ) graph.createGivenGraph(parameters);

        // Initialize Simulation and run it
        Simulation simulation = Simulation.getSimulation(parameters, graph);
        simulation.initSimulation();
        simulation.runSimulation();
    }
}
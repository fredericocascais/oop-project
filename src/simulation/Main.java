package simulation;
import aco.Ant;
import aco.AntMoveEvent;
import aco.Pheromones;
import graph.*;
import pec.IEvent;
import pec.PEC;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        if(args.length<2){
            System.out.println("Arguments Error");
            System.exit(1);
        }

        InputParameters parameters = new InputParameters(args);
        WeightedGraph graph = WeightedGraph.getGraph( parameters.getTotalNodes() );

        if( args[0].equals("-r") ) graph.createRandomGraph(parameters.getMaxWeight() );
        else if (args[0].equals("-f")) graph.createGivenGraph(parameters);

        Simulation simulation = Simulation.getSimulation(parameters, graph);



    }


}
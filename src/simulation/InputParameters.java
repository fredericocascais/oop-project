package simulation;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class InputParameters {
    private  int totalNodes;
    private int maxWeight;
    private int nestNode;
    private int alpha;
    private int beta;
    private int delta;
    private int eta;
    private int rho;
    private int gamma;
    private int colonySize;
    private int simulationTime;
    private String[] fileMatrix;


    public InputParameters(String[] args) throws FileNotFoundException {
        if(args[0].equals("-r")){
            AddParametersFromArguments(args);
        } else if (args[0].equals("-f")) {
            AddParametersFromFile(args);
        }
    }



    public void AddParametersFromFile(String[] args) throws FileNotFoundException {
        File input_file = new File(args[1]);
        Scanner scanner = new Scanner(input_file);

        String first_line = scanner.nextLine();  //Get parameters from 1st line
        String[] aux = first_line.split(" ");

        totalNodes = Integer.parseInt(aux[0]);
        nestNode = Integer.parseInt(aux[1]);
        alpha = Integer.parseInt(aux[2]);
        beta = Integer.parseInt(aux[3]);
        delta = Integer.parseInt(aux[4]);
        eta = Integer.parseInt(aux[5]); //n, evaporation event
        rho = Integer.parseInt(aux[6]); //p, evaporation event
        gamma = Integer.parseInt(aux[7]); //y, pheromone level
        colonySize = Integer.parseInt(aux[8]);
        simulationTime = Integer.parseInt(aux[9]);

        fileMatrix = new String[totalNodes];
        int nbr_lines=0;

        while(scanner.hasNextLine()){    //Get graph from remaining lines
            String fileline = scanner.nextLine();
            fileMatrix[nbr_lines] = fileline;
            nbr_lines+=1;
        }

    }

    public void AddParametersFromArguments(String[] args){
         totalNodes = Integer.parseInt(args[1]);
         maxWeight = Integer.parseInt(args[2]);
         nestNode = Integer.parseInt(args[3]); //nest node
         alpha = Integer.parseInt(args[4]);
         beta = Integer.parseInt(args[5]);
         delta = Integer.parseInt(args[6]);
         eta = Integer.parseInt(args[7]); //n, evaporation event
         rho = Integer.parseInt(args[8]); //p, evaporation event
         gamma = Integer.parseInt(args[9]); //y, pheromone level
         colonySize = Integer.parseInt(args[10]);
         simulationTime = Integer.parseInt(args[11]);
    }

    public int getAlpha() {
        return alpha;
    }

    public int getTotalNodes() {
        return totalNodes;
    }

    public int getMaxWeight() {
        return maxWeight;
    }

    public int getNestNode() {
        return nestNode;
    }

    public int getBeta() {
        return beta;
    }

    public int getDelta() {
        return delta;
    }

    public int getEta() {
        return eta;
    }

    public int getRho() {
        return rho;
    }

    public int getGamma() {
        return gamma;
    }

    public int getColonySize() {
        return colonySize;
    }

    public int getSimulationTime() {
        return simulationTime;
    }

    public String[] getFileMatrix() {
        return fileMatrix;
    }
}

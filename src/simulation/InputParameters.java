package simulation;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class InputParameters {
    private  int totalNodes;
    private int maxWeight;
    private int nestNode;
    private double alpha;
    private double beta;
    private double delta;
    private double eta;
    private double rho;
    private double gamma;
    private int colonySize;
    private double simulationMaxTime;
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
        alpha = Double.parseDouble(aux[2]);
        beta = Double.parseDouble(aux[3]);
        delta = Double.parseDouble(aux[4]);
        eta = Double.parseDouble(aux[5]); //n, evaporation event
        rho = Double.parseDouble(aux[6]); //p, evaporation event
        gamma = Double.parseDouble(aux[7]); //y, pheromone level
        colonySize = Integer.parseInt(aux[8]);
        simulationMaxTime = Double.parseDouble(aux[9]);
        maxWeight = -1;

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
         alpha = Double.parseDouble(args[4]);
         beta = Double.parseDouble(args[5]);
         delta = Double.parseDouble(args[6]);
         eta = Double.parseDouble(args[7]); //n, evaporation event
         rho = Double.parseDouble(args[8]); //p, evaporation event
         gamma = Double.parseDouble(args[9]); //y, pheromone level
         colonySize = Integer.parseInt(args[10]);
         simulationMaxTime = Double.parseDouble(args[11]);
    }

    public void printInputParameters(){
        System.out.println("Input Parameters:\n");
        System.out.println("\t\t\t" + totalNodes + ": number of nodes in the graph");
        System.out.println("\t\t\t" + nestNode + ": the nest node");
        System.out.println("\t\t\t" + alpha + ": alpha, ant move event");
        System.out.println("\t\t\t" + beta + ": beta, ant move event");
        System.out.println("\t\t\t" + delta + ": delta, ant move event");
        System.out.println("\t\t\t" + eta + ": eta, pheromone evaporation event");
        System.out.println("\t\t\t" + rho + ": rho, pheromone evaporation event");
        System.out.println("\t\t\t" + gamma + ": pheromone level");
        System.out.println("\t\t\t" + colonySize + ": ant colony size");
        System.out.println("\t\t\t" + simulationMaxTime + ": final instant");
    }

    public double getAlpha() {
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

    public double getBeta() {
        return beta;
    }

    public double getDelta() {
        return delta;
    }

    public double getEta() {
        return eta;
    }

    public double getRho() {
        return rho;
    }

    public double getGamma() {
        return gamma;
    }

    public int getColonySize() {
        return colonySize;
    }

    public double getMaxSimulationTime() {
        return simulationMaxTime;
    }

    public String[] getFileMatrix() {
        return fileMatrix;
    }
}

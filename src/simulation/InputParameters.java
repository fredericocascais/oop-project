package simulation;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * The InputParameters class represents the input parameters for the simulation.
 */
public class InputParameters {
    private int totalNodes;
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
    private List<int[]> fileMatrix;

    /**
     * Constructs an InputParameters object and initializes the parameters based on the command-line arguments.
     *
     * @param args the command-line arguments
     * @throws FileNotFoundException if the input file specified in the arguments is not found
     */
    public InputParameters(String[] args) throws FileNotFoundException {
        if (args[0].equals("-r")) {
            addParametersFromArguments(args);
        } else if (args[0].equals("-f")) {
            addParametersFromFile(args);
        }
    }

    /**
     * Sets the input parameters based on the values in the input file.
     *
     * @param args the command-line arguments
     * @throws FileNotFoundException if the input file specified in the arguments is not found
     */
    public void addParametersFromFile(String[] args) throws FileNotFoundException {
        String current_directory = System.getProperty("user.dir") + "/";
        File input_file;

        if (args[1].startsWith("./")) input_file = new File(current_directory + args[1].substring(2));
        else input_file = new File(current_directory + args[1]);

        Scanner scanner = new Scanner(input_file);

        String firstLine = scanner.nextLine();
        String[] aux = firstLine.split(" ");

        // Set properties according to the arguments
        totalNodes = Integer.parseInt(aux[0]);
        nestNode = Integer.parseInt(aux[1]);
        alpha = Double.parseDouble(aux[2]);
        beta = Double.parseDouble(aux[3]);
        delta = Double.parseDouble(aux[4]);
        eta = Double.parseDouble(aux[5]);
        rho = Double.parseDouble(aux[6]);
        gamma = Double.parseDouble(aux[7]);
        colonySize = Integer.parseInt(aux[8]);
        simulationMaxTime = Double.parseDouble(aux[9]);
        maxWeight = -1;

        // Prepare an array of String to then create a weighted graph
        fileMatrix = new ArrayList<>();
        int diagonalIndex = 0;
        while (scanner.hasNextLine()) {
            String fileLine = scanner.nextLine();
            String[] matrixFileLine = fileLine.split("\\s+");

            if(matrixFileLine.length != totalNodes) throw new RuntimeException("Invalid matrix : invalid size");

            int[] weights = new int[matrixFileLine.length];

            for(int i = 0;i < weights.length;i++)
            {
                // Note that this is assuming valid input
                // If you want to check then add a try/catch
                // and another index for the numbers if to continue adding the others (see below)
                weights[i] = Integer.parseInt(matrixFileLine[i]);
                if (weights[i] < 0) throw new RuntimeException("Invalid matrix : negative weight");
                if (weights[diagonalIndex] != 0) {
                    throw new RuntimeException("Invalid matrix : node cannot have an edge connecting to itself");
                }

            }
            fileMatrix.add(weights);
            diagonalIndex++;
        }

    }

    /**
     * Sets the input parameters based on the command-line arguments.
     *
     * @param args the command-line arguments
     */
    public void addParametersFromArguments(String[] args) {
        totalNodes = Integer.parseInt(args[1]);
        maxWeight = Integer.parseInt(args[2]);
        nestNode = Integer.parseInt(args[3]);
        alpha = Double.parseDouble(args[4]);
        beta = Double.parseDouble(args[5]);
        delta = Double.parseDouble(args[6]);
        eta = Double.parseDouble(args[7]);
        rho = Double.parseDouble(args[8]);
        gamma = Double.parseDouble(args[9]);
        colonySize = Integer.parseInt(args[10]);
        simulationMaxTime = Double.parseDouble(args[11]);
    }
    
    /**
     * Prints the input parameters.
     */
    public void printInputParameters() {
        System.out.println("Input Parameters:");
        System.out.println("\t\t\t\t" + totalNodes + ": number of nodes in the graph");
        System.out.println("\t\t\t\t" + nestNode + ": the nest node");
        System.out.println("\t\t\t\t" + alpha + ": alpha, ant move event");
        System.out.println("\t\t\t\t" + beta + ": beta, ant move event");
        System.out.println("\t\t\t\t" + delta + ": delta, ant move event");
        System.out.println("\t\t\t\t" + eta + ": eta, pheromone evaporation event");
        System.out.println("\t\t\t\t" + rho + ": rho, pheromone evaporation event");
        System.out.println("\t\t\t\t" + gamma + ": pheromone level");
        System.out.println("\t\t\t\t" + colonySize + ": ant colony size");
        System.out.println("\t\t\t\t" + simulationMaxTime + ": final instant");
    }

    /**
     * Returns the alpha value.
     *
     * @return the alpha value
     */
    public double getAlpha() {
        return alpha;
    }

    /**
     * Returns the total number of nodes in the graph.
     *
     * @return the total number of nodes
     */
    public int getTotalNodes() {
        return totalNodes;
    }

    /**
     * Returns the maximum weight.
     *
     * @return the maximum weight
     */
    public int getMaxWeight() {
        return maxWeight;
    }

    /**
     * Returns the nest node.
     *
     * @return the nest node
     */
    public int getNestNode() {
        return nestNode;
    }

    /**
     * Returns the beta value.
     *
     * @return the beta value
     */
    public double getBeta() {
        return beta;
    }

    /**
     * Returns the delta value.
     *
     * @return the delta value
     */
    public double getDelta() {
        return delta;
    }

    /**
     * Returns the eta value.
     *
     * @return the eta value
     */
    public double getEta() {
        return eta;
    }

    /**
     * Returns the rho value.
     *
     * @return the rho value
     */
    public double getRho() {
        return rho;
    }

    /**
     * Returns the gamma value.
     *
     * @return the gamma value
     */
    public double getGamma() {
        return gamma;
    }

    /**
     * Returns the colony size.
     *
     * @return the colony size
     */
    public int getColonySize() {
        return colonySize;
    }

    /**
     * Returns the maximum simulation time.
     *
     * @return the maximum simulation time
     */
    public double getMaxSimulationTime() {
        return simulationMaxTime;
    }

    /**
     * Returns the file matrix.
     *
     * @return the file matrix
     */
    public List<int[]> getFileMatrix() {
        return fileMatrix;
    }
}

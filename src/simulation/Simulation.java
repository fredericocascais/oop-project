package simulation;
import java.util.ArrayList;
import java.util.List;

import aco.Ant;
import graph.HamiltoneanCycle;
import graph.WeightedGraph;

public class Simulation {
    static private Simulation simulation;
    
    final int totalNodes;
    final int nestNode;
    final double alpha;
    final double beta;
    final double delta;
    final double eta;
    final double rho;
    final int colonySize;
    final double simulationTime;

    final WeightedGraph graph;

    List<Ant> antColony = new ArrayList<>();
    
    int totalMoves = 0;
    int totalEvaporations = 0;
    int currentTime = 0;
    List<HamiltoneanCycle> hamiltoneanCycleFound = new ArrayList<>();

    public Simulation(
        int totalNodes,
        int nestNode, 
        double alpha, 
        double beta, 
        double delta, 
        double eta, 
        double rho, 
        int colonySize, 
        double simulationTime,
        WeightedGraph graph
    ){
        this.totalNodes = totalNodes;
        this.nestNode = nestNode;
        this.alpha = alpha;
        this.beta = beta;
        this.delta = delta;
        this.eta = eta;
        this.rho = rho;
        this.colonySize = colonySize;
        this.simulationTime = simulationTime;
        this.graph = graph;
    }

    public static Simulation getSimulation(){
        return simulation;
    }

    public static Simulation getSimulation(
        int totalNodes, 
        int nestNode, 
        double alpha, 
        double beta, 
        double delta, 
        double eta, 
        double rho, 
        int colonySize, 
        double simulationTime,
        WeightedGraph graph
    ){
        if (simulation == null) { 
            simulation = new Simulation(totalNodes, nestNode, alpha, beta, delta, eta, rho, colonySize, simulationTime, graph);
        } return simulation;
    }

    public int getCurrentTime() {
      return currentTime;
    }

    public int getTotalNodes() {
        return totalNodes;
    }

    public int getNestNode() {
        return nestNode;
    }

    public double getAlpha() {
        return alpha;
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

    public int getColonySize() {
        return colonySize;
    }

    public double getSimulationTime() {
        return simulationTime;
    }

    public WeightedGraph getGraph() {
        return graph;
    }

    public List<Ant> getAntColony() {
        return antColony;
    }

    public int getTotalMoves() {
        return totalMoves;
    }

    public int getTotalEvaporations() {
        return totalEvaporations;
    }

    public List<HamiltoneanCycle> getHamiltoneanCycles() {
        return hamiltoneanCycleFound;
    }

    public void addHamiltoneanCycle(HamiltoneanCycle cycle){
        hamiltoneanCycleFound.add(cycle);
    }

    public void addAnt(Ant ant){
        antColony.add(ant);
    }

    public void increaseCurrentTime(double increment){
        currentTime+=increment;
    }

    public void increaseTotalMoves(){
        totalMoves+=1;
    }

    public void increaseTotalEvaporations() {
        totalEvaporations+=1;
    }
    
}
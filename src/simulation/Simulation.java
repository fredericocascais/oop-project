package simulation;
import java.util.ArrayList;
import java.util.List;

import aco.Ant;
import aco.AntMoveEvent;
import aco.Pheromones;
import graph.Edge;
import graph.HamiltoneanCycle;
import graph.WeightedGraph;
import pec.Event;
import pec.PEC;

public class Simulation {
    static private Simulation simulation;

    private final InputParameters parameters;

    private final WeightedGraph graph;

    private Pheromones pheromones;

    List<Ant> antColony;
    
    int totalMoves = 0;
    int totalEvaporations = 0;
    double currentTime = 0;
    List<HamiltoneanCycle> hamiltoneanCycleFound = new ArrayList<>();

    public Simulation(
        InputParameters parameters,
        WeightedGraph graph
    ){
        this.parameters = parameters;
        this.graph = graph;
        this.antColony = new ArrayList<>();
        this.pheromones = Pheromones.getPheromones(graph.getMaxEdges());

        for( int i = 0; i < parameters.getColonySize(); i++){
            Ant ant = new Ant( graph.getNode( getNestNode() ));
            antColony.add( new Ant( graph.getNode( getNestNode() )));
            Edge next_edge = ant.getNextChosenEdge();
            PEC.getPEC().addEvent(new AntMoveEvent(ant, next_edge));
        }
    }


    public static Simulation getSimulation(){
        return simulation;
    }

    public static Simulation getSimulation(
        InputParameters parameters,
        WeightedGraph graph
    ){
        if (simulation == null) { 
            simulation = new Simulation(parameters, graph);
        } return simulation;
    }

    public void runSimulation(){
        PEC pec = PEC.getPEC();
        while (currentTime < getSimulationTime() ){
            Event next_event = pec.getNextEvent();
            currentTime = next_event.getEventTime();
            next_event.executeEvent();

            checkHamiltonean()
        }
    }

    public double getCurrentTime() {
      return currentTime;
    }

    public int getTotalNodes() {
        return parameters.getTotalNodes();
    }

    public int getNestNode() {
        return parameters.getNestNode();
    }

    public double getAlpha() {
        return parameters.getAlpha();
    }

    public double getBeta() {
        return parameters.getBeta();
    }

    public double getDelta() {
        return parameters.getDelta();
    }

    public double getEta() {
        return parameters.getEta();
    }

    public double getRho() {
        return parameters.getRho();
    }

    public double getGamma() {
        return parameters.getGamma();
    }

    public int getColonySize() {
        return parameters.getColonySize();
    }

    public double getSimulationTime() {
        return parameters.getSimulationTime();
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
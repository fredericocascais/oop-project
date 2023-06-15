package simulation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import aco.Ant;
import aco.AntMoveEvent;
import aco.Pheromones;
import aco.SimulationReportEvent;
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
    double currentSimulationTime = 0;
    List<HamiltoneanCycle> hamiltoneanCycleFound = new ArrayList<>();

    private final PEC pec = PEC.getPEC();

    public Simulation(
        InputParameters parameters,
        WeightedGraph graph
    ){
        this.parameters = parameters;
        this.graph = graph;
        this.antColony = new ArrayList<>();
        this.pheromones = Pheromones.getPheromones(graph.getMaxEdges());
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

    public void initSimulation(){
        // Create Ants according to the colony size
        for( int i = 0; i < parameters.getColonySize(); i++){
            // For each new Ant choose an non visited Node to visit and create an Ant Move Event corresponding
            // to the Ant and the next Edge going to transverse to get to the chosen non visited Node
            Ant ant = new Ant( graph.getNode( getNestNode() - 1 ));
            antColony.add( ant );
            Edge next_edge = ant.getNextChosenEdge();
            pec.addEvent(new AntMoveEvent(ant, next_edge));
        }


        double report_iteration_time = getMaxSimulationTime()/20;
        int iter = 1;

        // Create 20 Simulation Reporting events
        // Each splitted in equal intervals between each other
        while (report_iteration_time * iter < getMaxSimulationTime()) {
            pec.addEvent( new SimulationReportEvent( hamiltoneanCycleFound, report_iteration_time * iter ));
            iter++;
        }
        pec.addEvent( new SimulationReportEvent( hamiltoneanCycleFound, getMaxSimulationTime() ));
    }

    public void runSimulation(){
        if (simulation == null) throw new RuntimeException("No instance of Simulation created");

        // Print initial parameters
        parameters.printInputParameters();
        graph.printGraph();

        // Until the current simulation time doesn't reach the final instant
        // Execute the most recent event
        while (currentSimulationTime < getMaxSimulationTime() ){
            Event next_event = pec.getNextEvent();
            next_event.executeEvent();
        }
    }

    public double getCurrentSimulationTime() {
      return currentSimulationTime;
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


    public double getGamma() { return parameters.getGamma(); }

    public double getMaxWeight() { return parameters.getMaxWeight(); }

    public int getColonySize() {
        return parameters.getColonySize();
    }

    public double getMaxSimulationTime() {
        return parameters.getMaxSimulationTime();
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
        boolean contains_cycle = false;
        for (HamiltoneanCycle hamiltonian_cycle : hamiltoneanCycleFound) {
            if (hamiltonian_cycle.equals( cycle )) {
                contains_cycle = true;
                break;
            }
        }
        if(!contains_cycle){
            hamiltoneanCycleFound.add(cycle);
            Collections.sort(hamiltoneanCycleFound);
        }
    }

    public void addAnt(Ant ant){
        antColony.add(ant);
    }

    public void increaseCurrentTime(double increment){
        currentSimulationTime = increment;
    }

    public void increaseTotalMoves(){
        totalMoves+=1;
    }

    public void increaseTotalEvaporations() {
        totalEvaporations+=1;
    }

    public void addNewEvent( Event new_event ){
        pec.addEvent(new_event);
    }

    public HamiltoneanCycle getBestCycle(){
        return hamiltoneanCycleFound.get(0);
    }

    public List<HamiltoneanCycle> getTopCandidateCycles(){
        if(hamiltoneanCycleFound.size() < 5){
            return hamiltoneanCycleFound.subList(1, hamiltoneanCycleFound.size());
        }
        return hamiltoneanCycleFound.subList(1,5);
    }
    
}
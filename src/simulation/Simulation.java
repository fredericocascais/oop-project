package simulation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import aco.Ant;
import aco.AntMoveEvent;
import aco.Pheromones;
import aco.SimulationReportEvent;
import graph.Edge;
import graph.HamiltonianCycle;
import graph.WeightedGraph;
import pec.Event;
import pec.PEC;

/**
 * The Simulation class represents the simulation environment for the Ant Colony Optimization (ACO) algorithm.
 */
public class Simulation {
    static private Simulation simulation;

    private final InputParameters parameters;

    private final WeightedGraph graph;

    private Pheromones pheromones;

    List<Ant> antColony;

    int totalMoves = 0;
    int totalEvaporations = 0;
    double currentSimulationTime = 0;
    List<HamiltonianCycle> hamiltonianCycleFound = new ArrayList<>();

    private final PEC pec = PEC.getPEC();

    /**
     * Constructs a Simulation object with the specified parameters and weighted graph.
     *
     * @param parameters The input parameters for the simulation.
     * @param graph The weighted graph representing the problem.
     */
    public Simulation(InputParameters parameters, WeightedGraph graph) {
        this.parameters = parameters;
        this.graph = graph;
        this.antColony = new ArrayList<>();
        this.pheromones = Pheromones.getPheromones(graph.getMaxEdges());
    }

    /**
     * Retrieves the current simulation instance.
     *
     * @return The current Simulation instance.
     */
    public static Simulation getSimulation() {
        return simulation;
    }

    /**
     * Retrieves or creates the current simulation instance with the specified parameters and weighted graph.
     *
     * @param parameters The input parameters for the simulation.
     * @param graph The weighted graph representing the problem.
     * @return The current Simulation instance.
     */
    public static Simulation getSimulation(InputParameters parameters, WeightedGraph graph) {
        if (simulation == null) {
            simulation = new Simulation(parameters, graph);
        }
        return simulation;
    }

    /**
     * Initializes the simulation by creating ants, scheduling events, and setting up reporting.
     */
    public void initSimulation() {
        // Create Ants according to the colony size
        for (int i = 0; i < parameters.getColonySize(); i++) {
            // For each new Ant choose a non-visited Node to visit and create an AntMoveEvent corresponding
            // to the Ant and the next Edge to traverse to reach the chosen non-visited Node
            Ant ant = new Ant(graph.getNode(getNestNode() - 1));
            antColony.add(ant);
            Edge nextEdge = ant.getNextChosenEdge();
            pec.addEvent(new AntMoveEvent(ant, nextEdge));
        }

        double reportIterationTime = getMaxSimulationTime() / 20;
        int iter = 1;

        // Create 20 SimulationReporting events
        // Each is split into equal intervals between each other
        while (reportIterationTime * iter < getMaxSimulationTime()) {
            pec.addEvent(new SimulationReportEvent(reportIterationTime * iter));
            iter++;
        }
        pec.addEvent(new SimulationReportEvent(getMaxSimulationTime()));
    }

    /**
     * Runs the simulation until the maximum simulation time is reached.
     */
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

    /**
     * Retrieves the current simulation time.
     *
     * @return The current simulation time.
     */
    public double getCurrentSimulationTime() {
        return currentSimulationTime;
    }

    /**
     * Retrieves the total number of nodes in the simulation.
     *
     * @return The total number of nodes.
     */
    public int getTotalNodes() {
        return parameters.getTotalNodes();
    }

    /**
     * Retrieves the nest node in the simulation.
     *
     * @return The nest node.
     */
    public int getNestNode() {
        return parameters.getNestNode();
    }

    /**
     * Retrieves the alpha value used in the simulation.
     *
     * @return The alpha value.
     */
    public double getAlpha() {
        return parameters.getAlpha();
    }

    /**
     * Retrieves the beta value used in the simulation.
     *
     * @return The beta value.
     */
    public double getBeta() {
        return parameters.getBeta();
    }

    /**
     * Retrieves the delta value used in the simulation.
     *
     * @return The delta value.
     */
    public double getDelta() {
        return parameters.getDelta();
    }

    /**
     * Retrieves the eta value used in the simulation.
     *
     * @return The eta value.
     */
    public double getEta() {
        return parameters.getEta();
    }

    /**
     * Retrieves the rho value used in the simulation.
     *
     * @return The rho value.
     */
    public double getRho() {
        return parameters.getRho();
    }

    /**
     * Retrieves the gamma value used in the simulation.
     *
     * @return The gamma value.
     */
    public double getGamma() {
        return parameters.getGamma();
    }

    /**
     * Retrieves the maximum weight value used in the simulation.
     *
     * @return The maximum weight value.
     */
    public double getMaxWeight() {
        return parameters.getMaxWeight();
    }

    /**
     * Retrieves the colony size used in the simulation.
     *
     * @return The colony size.
     */
    public int getColonySize() {
        return parameters.getColonySize();
    }

    /**
     * Retrieves the maximum simulation time.
     *
     * @return The maximum simulation time.
     */
    public double getMaxSimulationTime() {
        return parameters.getMaxSimulationTime();
    }

    /**
     * Retrieves the weighted graph used in the simulation.
     *
     * @return The weighted graph.
     */
    public WeightedGraph getGraph() {
        return graph;
    }

    /**
     * Retrieves the list of ants in the simulation.
     *
     * @return The list of ants.
     */
    public List<Ant> getAntColony() {
        return antColony;
    }

    /**
     * Retrieves the total number of moves made by ants in the simulation.
     *
     * @return The total number of moves.
     */
    public int getTotalMoves() {
        return totalMoves;
    }

    /**
     * Retrieves the total number of evaporations performed in the simulation.
     *
     * @return The total number of evaporations.
     */
    public int getTotalEvaporations() {
        return totalEvaporations;
    }

    /**
     * Retrieves the list of Hamiltonian cycles found in the simulation.
     *
     * @return The list of Hamiltonian cycles.
     */
    public List<HamiltonianCycle> getHamiltonianCycles() {
        return hamiltonianCycleFound;
    }

    /**
     * Adds a Hamiltonian cycle to the list of found cycles in the simulation if it does not already exist.
     * The cycles are sorted in ascending order based on their cost.
     *
     * @param cycle The Hamiltonian cycle to add.
     */
    public void addHamiltonianCycle(HamiltonianCycle cycle) {
        boolean containsCycle = false;
        for (HamiltonianCycle hamiltonianCycle : hamiltonianCycleFound) {
            if (hamiltonianCycle.equals(cycle)) {
                containsCycle = true;
                break;
            }
        }
        if (!containsCycle) {
            hamiltonianCycleFound.add(cycle);
            Collections.sort(hamiltonianCycleFound);
        }
    }

    
    /**
     * Adds an Ant to the ant colony.
     *
     * @param ant The Ant to add.
     */
    public void addAnt(Ant ant){
        antColony.add(ant);
    }

    /**
     * Increases the current simulation time by the specified increment.
     *
     * @param increment The time increment to add.
     */
    public void increaseCurrentTime(double increment){
        currentSimulationTime = increment;
    }

    /**
     * Increases the total number of moves performed in the simulation by 1.
     */
    public void increaseTotalMoves(){
        totalMoves+=1;
    }

    /**
     * Increases the total number of evaporations performed in the simulation by 1.
     */
    public void increaseTotalEvaporations() {
        totalEvaporations+=1;
    }

    /**
     * Adds a new event to the PEC (Primary Event Container).
     *
     * @param new_event The event to add.
     */
    public void addNewEvent( Event new_event ){
        pec.addEvent(new_event);
    }

    /**
     * Retrieves the best Hamiltonian cycle found in the simulation.
     *
     * @return The best Hamiltonian cycle.
     */
    public HamiltonianCycle getBestCycle(){
        return hamiltonianCycleFound.get(0);
    }

    /**
     * Retrieves the top candidate Hamiltonian cycles found in the simulation, excluding the best cycle.
     *
     * @return The top candidate Hamiltonian cycles.
     */
    public List<HamiltonianCycle> getTopCandidateCycles(){
        if(hamiltonianCycleFound.size() < 5){
            return hamiltonianCycleFound.subList(1, hamiltonianCycleFound.size());
        }
        return hamiltonianCycleFound.subList(1,5);
    }
    
}
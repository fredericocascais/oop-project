package aco;

import graph.HamiltonianCycle;
import pec.Event;
import simulation.Simulation;


import static java.lang.System.exit;

/**
 * The SimulationReportEvent class represents a report of the simulation at a specific time.
 * It prints various statistics and information about the current state of the simulation.
 */
public class SimulationReportEvent extends Event {
    /**
     * The Simulation instance used for accessing simulation-related functionality.
     */
    private final Simulation simulation = Simulation.getSimulation();

    /**
     * The iteration number for the current object instance.
     */
    private final int iteration;

    /**
     * The increment value used for iteration numbering.
     */
    private static int increment = 1;

    /**
     * Constructs a SimulationReportEvent object with the specified event time.
     *
     * @param eventTime The time at which the event occurs.
     */
    public SimulationReportEvent(double eventTime) {
        setEventTime(eventTime, "true");
        setEventType("simulation-report");
        iteration = increment;
        increment++;
    }

    /**
     * Executes the simulation report event by printing the statistics and information about the simulation.
     */
    @Override
    public void executeEvent() {
        System.out.println("\n\nObservation " + iteration + ":");
        System.out.println("\t\t\t\t" + "Present instant:               " + simulation.getCurrentSimulationTime());
        System.out.println("\t\t\t\t" + "Number of move events:         " + simulation.getTotalMoves());
        System.out.println("\t\t\t\t" + "Number of evaporation events:  " + simulation.getTotalEvaporations());
        System.out.println("\t\t\t\t" + "Top candidate cycles:          ");
        if (simulation.getHamiltonianCycles().size() > 1) {
            for (HamiltonianCycle hamiltonianCycle : simulation.getTopCandidateCycles()) {
                System.out.println("\t\t\t\t" + "                               " + hamiltonianCycle);
            }
        }
        if (simulation.getHamiltonianCycles().size() > 0) {
            System.out.println("\n\t\t\t\t" + "Best Hamiltonian:              " + simulation.getBestCycle());
        } else {
            System.out.println("\n\t\t\t\t" + "Best Hamiltonian:              " + "{}");
        }
        if (iteration == 20) exit(0);
    }
}

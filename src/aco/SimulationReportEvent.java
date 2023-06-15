package aco;

import graph.HamiltoneanCycle;
import pec.Event;
import simulation.Simulation;

import java.util.List;

public class SimulationReportEvent extends Event {
    private final Simulation simulation = Simulation.getSimulation();

    private final int iteration;
    private static int increment = 1;

    public SimulationReportEvent(double eventTime) {
        setEventTime(eventTime, "true");
        setEventType("simulation-report");
        iteration = increment;
        increment++;
    }

    @Override
    public void executeEvent() {
        System.out.println("Observation " + iteration + ":");
        System.out.println("\t\t\t\t" + "Present instant:               " + simulation.getCurrentSimulationTime());
        System.out.println("\t\t\t\t" + "Number of move events:         " + simulation.getTotalMoves());
        System.out.println("\t\t\t\t" + "Number of evaporation events:  " + simulation.getTotalEvaporations());
        System.out.println("\t\t\t\t" + "Top candidate cycles:          ");
        if (simulation.getHamiltoneanCycles().size() > 1) {
            for (HamiltoneanCycle hamiltoneanCycle : simulation.getTopCandidateCycles()) {
                System.out.println("\t\t\t\t" + "                               " + hamiltoneanCycle);
            }
        }
        if (simulation.getHamiltoneanCycles().size() > 0) {
            System.out.println("\t\t\t\t" + "Best Hamiltonian:              " + simulation.getBestCycle());
        } else {
            System.out.println("\t\t\t\t" + "Best Hamiltonian:              " + "{}");
        }
    }
}

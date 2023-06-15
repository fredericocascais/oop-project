package aco;

import graph.HamiltoneanCycle;
import pec.Event;
import simulation.Simulation;

import java.util.List;

public class SimulationReportEvent extends Event {
    private final Simulation simulation = Simulation.getSimulation();
    private List<HamiltoneanCycle> hamiltoneanCycles;

    public SimulationReportEvent(List<HamiltoneanCycle> hamiltoneanCycles, double eventTime) {
        this.hamiltoneanCycles = hamiltoneanCycles;
        setEventTime(eventTime, "true");
        setEventType("simulation-report");
    }

    @Override
    public void executeEvent() {
        System.out.println("\n\tHamiltonean cycles found:\n");
        System.out.println("\t\t" + hamiltoneanCycles);
    }
}

package pec;

import java.util.Random;

import simulation.Simulation;

/**
 * The Event class represents an event in the simulation.
 * It implements the IEvent interface and provides common functionality
 * for managing event time and event type.
 */
public abstract class Event implements IEvent, Comparable<Event> {
    private double event_time;  // Time at which the event will occur
    private String event_type;  // Type of the event

    private final Simulation simulation = Simulation.getSimulation();  // Reference to the simulation instance

    private final Random rand = new Random();  // Random number generator

    /**
     * Sets the event time using an exponential distribution with the specified mean.
     *
     * @param mean the mean value for the exponential distribution
     */
    public void setEventTime(double mean) {
        event_time = simulation.getCurrentSimulationTime() + expDistribution(mean);
    }

    /**
     * Sets the event time to a specific value, optionally specifying it manually.
     *
     * @param time   the event time
     * @param manual a flag indicating if the event time is set manually
     */
    public void setEventTime(double time, String manual) {
        if (manual.equals("true")) {
            event_time = time;
        }
    }

    /**
     * Returns the event time.
     *
     * @return the event time
     */
    public double getEventTime() {
        return event_time;
    }

    /**
     * Returns the event type.
     *
     * @return the event type
     */
    public String getEventType() {
        return event_type;
    }

    /**
     * Sets the event type.
     *
     * @param type the event type
     */
    public void setEventType(String type) {
        event_type = type;
    }

    /**
     * Generates a random number from an exponential distribution with the specified mean.
     *
     * @param mean the mean value for the exponential distribution
     * @return the generated random number
     */
    private double expDistribution(double mean) {
        double next = rand.nextDouble();
        return -mean * Math.log(1.0 - next);
    }

    /**
     * Adds the event time to the simulation's current time.
     */
    public void addEventTimeToSimulation() {
        Simulation simulation = Simulation.getSimulation();
        simulation.increaseCurrentTime(event_time);
    }

    /**
     * Compares this event with another event based on their event times.
     *
     * @param o the other event to compare
     * @return a negative integer, zero, or a positive integer if this event is less than, equal to, or greater than the other event
     */
    @Override
    public int compareTo(Event o) {
        return Double.compare(this.event_time, o.getEventTime());
    }
}

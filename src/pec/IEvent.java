package pec;

/**
 * The IEvent interface defines the contract for an event in the simulation.
 * It provides methods for setting/getting event time, event type, and executing the event.
 */
public interface IEvent {
    /**
     * Sets the event time based on the specified mean value.
     *
     * @param mean the mean value for the event time
     */
    void setEventTime(double mean);

    /**
     * Returns the event time.
     *
     * @return the event time
     */
    double getEventTime();

    /**
     * Returns the event type.
     *
     * @return the event type
     */
    String getEventType();

    /**
     * Sets the event type.
     *
     * @param type the event type
     */
    void setEventType(String type);

    /**
     * Adds the event time to the simulation's current time.
     */
    void addEventTimeToSimulation();

    /**
     * Executes the event.
     */
    void executeEvent();
}

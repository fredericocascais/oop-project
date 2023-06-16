package pec;


import java.util.Collections;
import java.util.LinkedList;


/**
 * The PEC (Pending Event Container) class represents the event control system in the simulation.
 * It manages the list of events and provides methods for adding, retrieving, and checking events.
 */
public class PEC {
    /**
     * List of events in the PEC.
     */
    private final LinkedList<Event> eventList;

    /**
     * Singleton instance of the PEC.
     */
    private static final PEC instance = new PEC();

    /**
     * Constructs a PEC object.
     */
    private PEC() {
        eventList = new LinkedList<>();
    }

    /**
     * Returns the singleton instance of the PEC class.
     *
     * @return the PEC instance
     */
    public static PEC getPEC() {
        return instance;
    }

    /**
     * Returns the next event in the event list.
     *
     * @return the next event
     */
    public Event getNextEvent() {
        return eventList.remove();
    }


    /**
     * Adds a new event to the event list and sorts the list based on event time.
     *
     * @param newEvent the new event to be added
     */
    public void addEvent(Event newEvent) {
        eventList.add(newEvent);
        Collections.sort(eventList);
    }
}
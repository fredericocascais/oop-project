package pec;


import java.util.Collections;
import java.util.LinkedList;


/**
 * The PEC (Pseudo Event Control) class represents the event control system in the simulation.
 * It manages the list of events and provides methods for adding, retrieving, and checking events.
 */
public class PEC {
    private final LinkedList<Event> eventList;
    private static final PEC instance = new PEC();

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


//    @Override
//    public String toString() {
//        return eventList.get(eventIndex).toString();
//    }
}
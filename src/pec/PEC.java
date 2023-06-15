package pec;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The PEC (Pseudo Event Control) class represents the event control system in the simulation.
 * It manages the list of events and provides methods for adding, retrieving, and checking events.
 */
public class PEC {
    private final List<Event> eventList;
    private int eventIndex = 0;
    private static final PEC instance = new PEC();

    private PEC() {
        eventList = new ArrayList<>();
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
        return eventList.get(eventIndex++);
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


    @Override
    public String toString() {
        return eventList.get(eventIndex).toString();
    }
}
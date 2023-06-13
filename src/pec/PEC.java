package pec;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PEC {
    List<Event> event_list;
    private int event_index = 0;

    public PEC(){
        event_list = new ArrayList<>();
    }

    public Event getNextEvent(){
        return event_list.get(event_index++);
    }
    public Event getCurrEvent(){
        return event_list.get(event_index);
    }
    public void addEvent(Event new_event){
        event_list.add(new_event);
        Collections.sort(event_list);

    }
    @Override
    public String toString(){

        return event_list.get(event_index)+"";
    }

}


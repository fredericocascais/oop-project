package pec;

import java.util.Random;

public abstract class Event implements IEvent, Comparable<Event>{
    private double event_time;

    private String event_type;

    private Random rand = new Random();
    public void setEventTime(double time, double mean){
        event_time = time + expDistribution(mean);
    }
    public double getEventTime(){
        return event_time;
    }
    public String getEventType(){
        return event_type;
    }

    public void setEventType( String type){
        event_type = type;
    }

    private double expDistribution(double mean){
        double next = rand.nextDouble();
        return -mean * Math.log(1.0 - next);
    }

    @Override
    public int compareTo(Event o) {
        return Double.compare(this.event_time, o.getEventTime());
    }
}

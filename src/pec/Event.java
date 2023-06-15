package pec;

import java.util.Random;

import simulation.Simulation;

public abstract class Event implements IEvent, Comparable<Event>{
    private double event_time;

    private String event_type;

    private final Simulation simulation = Simulation.getSimulation();

    private Random rand = new Random();
    public void setEventTime(double mean){
        event_time = simulation.getCurrentSimulationTime() + expDistribution(mean);
    }

    public void setEventTime(double time, String manual){
        if(manual.equals("true")) event_time = time;

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

    public void addEventTimeToSimulation(){
        Simulation simulation = Simulation.getSimulation();
        simulation.increaseCurrentTime(event_time);
    }

    @Override
    public int compareTo(Event o) {
        return Double.compare(this.event_time, o.getEventTime());
    }
}

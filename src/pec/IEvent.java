package pec;

public interface IEvent {
    public void setEventTime(double mean);
    public double getEventTime();
    public String getEventType();
    public void setEventType(String type);
    public void addEventTimeToSimulation();

    public Object executeEvent();

}

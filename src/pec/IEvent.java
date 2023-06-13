package pec;

public interface IEvent {
    public void setEventTime(double time, double mean);
    public double getEventTime();
    public String getEventType();
    public void setEventType(String type);

    public Object executeEvent();

}

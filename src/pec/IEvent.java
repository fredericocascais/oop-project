package pec;

public interface IEvent {
    void setEventTime(double mean);
    double getEventTime();
    String getEventType();
    void setEventType(String type);
    void addEventTimeToSimulation();
    void executeEvent();

}

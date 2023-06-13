package aco;

import graph.Edge;
import pec.Event;

public abstract class EvaporationEvent extends Event {
    private final double p;
    private final double n;

    private final Edge edge;

    public EvaporationEvent(double time, double p, double n, Edge edge){
        this.p = p;
        this.n = n;
        this.edge = edge;

        setEventTime( time, n );
        setEventType("evaporation");
    }

    public Edge getEdge(){
        return edge;
    }

}

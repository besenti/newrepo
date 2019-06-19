package com.assignment.shortestpath.model;

import com.assignment.shortestpath.model.Node;

public class Edge {
    private final String id;
    private final Node source;
    private final Node destination;
    private final Float weight;
//    private final Float traffic;

    public Edge(String id, Node source, Node destination, Float weight) {
        this.id = id;
        this.source = source;
        this.destination = destination;
        this.weight = weight;
//        this.traffic = traffic;
    }

    public String getId() {
        return id;
    }
    public Node getDestination() {
        return destination;
    }

    public Node getSource() {
        return source;
    }
    public Float getWeight() {
        return weight;
    }

//    public Float getTraffic(){
//        return traffic;
//    }

    @Override
    public String toString() {
        return source + " " + destination;
    }


}

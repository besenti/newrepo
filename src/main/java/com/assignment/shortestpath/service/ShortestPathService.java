package com.assignment.shortestpath.service;

import com.assignment.shortestpath.entity.Planet;
import com.assignment.shortestpath.entity.Route;
import com.assignment.shortestpath.model.Edge;
import com.assignment.shortestpath.model.Node;
import com.assignment.shortestpath.repository.PlanetRepository;
import com.assignment.shortestpath.repository.RouteRepository;
import com.assignment.shortestpath.repository.TrafficRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
public class ShortestPathService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShortestPathService.class);

    private  List<Node> nodes = new ArrayList<>();
    private  List<Edge> edges = new ArrayList<>();
    private Set<Node> settledNodes;
    private Set<Node> unSettledNodes;
    private Map<Node, Node> predecessors;
    private Map<Node, Float> distance;

    @Autowired
    PlanetRepository planetRepository;
    @Autowired
    RouteRepository routeRepository;
    @Autowired
    TrafficRepository trafficRepository;

    @PostConstruct
    public void init (){

        List<Planet> planets = (List<Planet>) planetRepository.findAll();

        LOGGER.info("Got planets:"+ planets.size());

        planets.forEach(planet -> {
            nodes.add(new Node(planet.getId().toString(),planet.getName()));
        });

        List<Route> routes = (List<Route>) routeRepository.findAll();

        routes.forEach(route -> {

            Optional<Planet> origin  = Optional.ofNullable(planetRepository.findPlanetByNode(route.getOrigin()));
            Optional<Planet> destination  = Optional.ofNullable(planetRepository.findPlanetByNode(route.getOrigin()));
            if (origin.isPresent() && destination.isPresent()){
                Node source = new Node(origin.get().getId().toString(),origin.get().getName());
                Node dest = new Node(destination.get().getId().toString(),destination.get().getName());
                edges.add(new Edge(route.getId().toString(),source, dest, route.getDistance()));
            }
        });

    }

    public LinkedList<Node> getPath(String origin, String destination){
        LOGGER.info("About to process..origin : "+ origin + " destination : " + destination);

        Optional<Planet> src  = Optional.ofNullable(planetRepository.findPlanetByNode(origin));
        Optional<Planet> dest  = Optional.ofNullable(planetRepository.findPlanetByNode(destination));

        LOGGER.info("Looked source as :" + src.get().getName());
        LOGGER.info("Looked destination as :"+ dest.get().getName());

        if (src.isPresent() && dest.isPresent()){

            //do Dijkstra algorithm.
            Node source = new Node(src.get().getId().toString(),src.get().getName());
            Node target = new Node(dest.get().getId().toString(),dest.get().getName());

            settledNodes = new HashSet<Node>();
            unSettledNodes = new HashSet<Node>();
            distance = new HashMap<Node, Float>();
            predecessors = new HashMap<Node, Node>();
            distance.put(source, 0F);
            unSettledNodes.add(source);
            while (unSettledNodes.size() > 0) {
                Node node = getMinimum(unSettledNodes);
                LOGGER.info("Processing loop...."+ node.getName());
                settledNodes.add(node);
                unSettledNodes.remove(node);
                findMinimalDistances(node);
            }

            LinkedList<Node> path = new LinkedList<Node>();
            Node step = target;
            // check if a path exists
            if (predecessors.get(step) == null) {
                return null;
            }
            path.add(step);
            while (predecessors.get(step) != null) {
                step = predecessors.get(step);
                path.add(step);
            }
            // Put it into the correct order
            Collections.reverse(path);
            return path;

        }
        return null;
    }

    private void findMinimalDistances(Node node) {
        List<Node> adjacentNodes = getNeighbors(node);
        for (Node target : adjacentNodes) {
            if (getShortestDistance(target) > getShortestDistance(node)
                    + getDistance(node, target)) {
                distance.put(target, getShortestDistance(node)
                        + getDistance(node, target));
                predecessors.put(target, node);
                unSettledNodes.add(target);
            }
        }

    }

    private Float getDistance(Node node, Node target) {
        for (Edge edge : edges) {
            if (edge.getSource().equals(node)
                    && edge.getDestination().equals(target)) {
                return edge.getWeight();
            }
        }
        throw new RuntimeException("Should not happen");
    }

    private List<Node> getNeighbors(Node node) {
        List<Node> neighbors = new ArrayList<Node>();
        for (Edge edge : edges) {
            if (edge.getSource().equals(node)
                    && !isSettled(edge.getDestination())) {
                neighbors.add(edge.getDestination());
            }
        }
        return neighbors;
    }

    private Node getMinimum(Set<Node> nodes) {
        Node minimum = null;
        for (Node node : nodes) {
            if (minimum == null) {
                minimum = node;
            } else {
                if (getShortestDistance(node) < getShortestDistance(minimum)) {
                    minimum = node;
                }
            }
        }
        return minimum;
    }

    private boolean isSettled(Node node) {
        return settledNodes.contains(node);
    }

    private Float getShortestDistance(Node destination) {
        Float d = distance.get(destination);
        if (d == null) {
            return Float.MAX_VALUE;
        } else {
            return d;
        }
    }

}


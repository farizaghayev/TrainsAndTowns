package com.farizaghayev.app;

import com.farizaghayev.Properties;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;


public class ServiceMapImpl implements ServiceMap {



    private Graph graph;
    private Map<String, Node> nodeMapper;

    public ServiceMapImpl() {
        graph = new Graph();
        nodeMapper = new HashMap< >();
    }


    private void addNode(Node node) {
        graph.addNode(node);

        if (!nodeMapper.containsKey(node.getName())) {
            nodeMapper.put(node.getName(), node);
        }


    }


    @Override
    public void parseInput(String input) {
        if (input.length() < 2) {
            throw new IllegalArgumentException(Properties.getProperties().get("message.graph.parse_input_format"));
        } else {
            String start = new String(String.valueOf(input.charAt(0)));
            String dest = new String(String.valueOf(input.charAt(1)));

            int weight = 0;

            if (input.length() > 2) {

                try {
                    weight = Integer.parseInt(input.substring(2));
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException(Properties.propertyWithArgs("message.graph.parse_input_weight",input));
                }
            }

            Node startNode = new Node(start);
            Node destNode = new Node(dest);

            addNode(startNode);
            addNode(destNode);

            graph.addEdge(startNode, destNode, weight);


        }
    }


    @Override
    public void init(String path) throws FileNotFoundException {
        Scanner in = new Scanner(new FileInputStream(path));

        while (in.hasNext()) {
            String input = in.next();
            parseInput(input);
        }
    }


    @Override
    public String shortestPath(String start, String dest) throws NoSuchEdgeException {
        try {
            Graph.GraphPath path = graph.distanceShortestPath(nodeMapper.get(start), nodeMapper.get(dest));

            return path.toString();
        } catch (Graph.NodeNotReachableException | NoSuchElementException e) {
            throw new NoSuchEdgeException(e.getMessage());
        }
    }


    @Override
    public int distanceShortestPath(String start, String dest) throws NoSuchEdgeException {
        try {
            Graph.GraphPath path = graph.distanceShortestPath(nodeMapper.get(start), nodeMapper.get(dest));

            return path.distance();
        } catch (Graph.NodeNotReachableException | NoSuchElementException e) {
            throw new NoSuchEdgeException(e.getMessage());
        }
    }

    @Override
    public int distance(String... nodes) throws NoSuchEdgeException {
        List<Node> nodeList = new ArrayList<>();
        for (String townName : nodes) {
            nodeList.add(nodeMapper.get(townName));
        }

        try {
            return graph.distance(nodeList);
        } catch (NoSuchElementException | Graph.NodeNotReachableException e) {
            throw new NoSuchEdgeException(e.getMessage());
        }
    }

    @Override
    public int countEdgesMaxStop(String start, String dest, int maxStop) {
        return graph.countEdgesMaxStop(nodeMapper.get(start), nodeMapper.get(dest), maxStop);
    }


    @Override
    public int countEdgesWithStop(String start, String dest, int stops) {
        return graph.countEdgesWithStop(nodeMapper.get(start), nodeMapper.get(dest), stops);
    }


    @Override
    public int countEdgesMaxDistance(String start, String dest, int maxDistance) {
        return graph.countEdgesMaxDistance(nodeMapper.get(start), nodeMapper.get(dest), maxDistance);
    }

    public static class NoSuchEdgeException extends RuntimeException {
        public NoSuchEdgeException(String message) {
            super(message);
        }
    }
}

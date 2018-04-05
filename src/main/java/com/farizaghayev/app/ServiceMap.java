package com.farizaghayev.app;

import java.io.FileNotFoundException;


public interface ServiceMap {


    void parseInput(String input);


    void init(String path) throws FileNotFoundException;


    String shortestPath(String start, String dest) throws ServiceMapImpl.NoSuchEdgeException;


    int distanceShortestPath(String start, String dest) throws ServiceMapImpl.NoSuchEdgeException;


    int distance(String... nodes) throws ServiceMapImpl.NoSuchEdgeException;


    int countEdgesMaxStop(String start, String dest, int maxStop);


    int countEdgesWithStop(String start, String dest, int stops);


    int countEdgesMaxDistance(String start, String dest, int maxDistance);

}

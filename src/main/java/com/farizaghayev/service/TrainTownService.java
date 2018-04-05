package com.farizaghayev.service;


public interface TrainTownService {

    String shortestPath(String start, String dest);


    int distanceShortestPath(String start, String dest);


    int distance(String... nodes);


    int countEdgesMaxStop(String start, String dest, int maxStops);


    int countEdgesWithStop(String start, String dest, int stops);


    int countEdgesMaxDistance(String start, String dest, int maxDistance);

}

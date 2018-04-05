package com.farizaghayev.service;

import com.farizaghayev.app.ServiceMapImpl;
import com.farizaghayev.app.ServiceMap;
import com.farizaghayev.Properties;


public class TrainTownServiceImpl implements TrainTownService {

    private ServiceMap map;

    public TrainTownServiceImpl(ServiceMap map) {
        this.map = map;
    }


    @Override
    public String shortestPath(String start, String dest) {
        try {
            return map.shortestPath(start, dest);
        } catch (ServiceMapImpl.NoSuchEdgeException e) {
            throw new TrainTownServiceException(Properties.getProperties().get("message.no_route"));
        }
    }


    @Override
    public int distanceShortestPath(String start, String dest) {
        try {
            return map.distanceShortestPath(start, dest);
        } catch (ServiceMapImpl.NoSuchEdgeException e) {
            throw new TrainTownServiceException(Properties.getProperties().get("message.no_route"));
        }
    }


    @Override
    public int distance(String... nodes) {
        try {
            return map.distance(nodes);
        } catch (ServiceMapImpl.NoSuchEdgeException e) {
            throw new TrainTownServiceException(Properties.getProperties().get("message.no_route"));
        }
    }


    @Override
    public int countEdgesMaxStop(String start, String dest, int maxStops) {
        return map.countEdgesMaxStop(start, dest, maxStops);
    }


    @Override
    public int countEdgesWithStop(String start, String dest, int stops) {
        return map.countEdgesWithStop(start, dest, stops);
    }


    @Override
    public int countEdgesMaxDistance(String start, String dest, int maxDistance) {
        return map.countEdgesMaxDistance(start, dest, maxDistance);
    }

}

package com.farizaghayev.factory.commands;

import com.farizaghayev.factory.AbstractStartDestinationCommand;
import com.farizaghayev.service.TrainTownService;



public class CountEdgesMaxDistanceCommand extends AbstractStartDestinationCommand {
    private int maxDistance;


    public CountEdgesMaxDistanceCommand(TrainTownService service) {
        super(service);
    }


    public void setMaxDistance(int maxDistance) {
        this.maxDistance = maxDistance;
    }


    @Override
    public Integer execute() {
        return getReceiver().countEdgesMaxDistance(start, dest, maxDistance);
    }
}

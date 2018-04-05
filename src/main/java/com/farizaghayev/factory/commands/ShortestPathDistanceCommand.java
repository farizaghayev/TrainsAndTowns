package com.farizaghayev.factory.commands;

import com.farizaghayev.factory.AbstractStartDestinationCommand;
import com.farizaghayev.service.TrainTownService;



public class ShortestPathDistanceCommand extends AbstractStartDestinationCommand {

    public ShortestPathDistanceCommand(TrainTownService service) {
        super(service);
    }


    @Override
    public Integer execute() {
        return getReceiver().distanceShortestPath(start, dest);
    }
}

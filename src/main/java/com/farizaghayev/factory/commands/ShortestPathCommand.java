package com.farizaghayev.factory.commands;

import com.farizaghayev.factory.AbstractStartDestinationCommand;
import com.farizaghayev.service.TrainTownService;


public class ShortestPathCommand extends AbstractStartDestinationCommand {

    public ShortestPathCommand(TrainTownService service) {
        super(service);
    }


    @Override
    public String execute() {
        return getReceiver().shortestPath(start, dest);
    }
}

package com.farizaghayev.factory.commands;

import com.farizaghayev.factory.AbstractStartDestinationCommand;
import com.farizaghayev.service.TrainTownService;



public class CountEdgesMaxStopsCommand extends AbstractStartDestinationCommand {
    private int maxStops;


    public CountEdgesMaxStopsCommand(TrainTownService service) {
        super(service);
    }


    public void setMaxStops(int maxStops) {
        this.maxStops = maxStops;
    }


    @Override
    public Integer execute() {
        return getReceiver().countEdgesMaxStop(start, dest, maxStops);
    }
}

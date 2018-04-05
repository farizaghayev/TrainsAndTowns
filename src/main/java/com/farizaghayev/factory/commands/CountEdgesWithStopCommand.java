package com.farizaghayev.factory.commands;

import com.farizaghayev.factory.AbstractStartDestinationCommand;
import com.farizaghayev.service.TrainTownService;



public class CountEdgesWithStopCommand extends AbstractStartDestinationCommand {
    private int stops;


    public CountEdgesWithStopCommand(TrainTownService service) {
        super(service);
    }



    public void setStops(int stops) {
        this.stops = stops;
    }


    @Override
    public Integer execute() {
        return getReceiver().countEdgesWithStop(start, dest, stops);
    }
}

package com.farizaghayev.factory;

import com.farizaghayev.service.TrainTownService;


public abstract class AbstractStartDestinationCommand extends AbstractServiceCommand {

    protected String start;

    protected String dest;


    public AbstractStartDestinationCommand(TrainTownService service) {
        super(service);
    }


    public void setStart(String start) {
        this.start = start;
    }


    public void setDest(String dest) {
        this.dest = dest;
    }
}

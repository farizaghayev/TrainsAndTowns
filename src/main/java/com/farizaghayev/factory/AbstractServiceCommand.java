package com.farizaghayev.factory;

import com.farizaghayev.service.TrainTownService;


public abstract class AbstractServiceCommand implements Command {

    private TrainTownService receiver;

    public AbstractServiceCommand(TrainTownService service){
        this.receiver = service;
    }

    protected TrainTownService getReceiver() {

        return this.receiver;

    }
}

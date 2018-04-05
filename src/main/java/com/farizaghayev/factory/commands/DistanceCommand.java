package com.farizaghayev.factory.commands;

import com.farizaghayev.factory.AbstractServiceCommand;
import com.farizaghayev.service.TrainTownService;



public class DistanceCommand extends AbstractServiceCommand {

    private String[] nodes;


    public DistanceCommand(TrainTownService service) {

        super(service);

    }


    public void setNodeNames(String[] nodes) {
        this.nodes = nodes;
    }


    @Override
    public Integer execute() {
        return getReceiver().distance(nodes);
    }
}

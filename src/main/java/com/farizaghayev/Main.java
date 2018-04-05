package com.farizaghayev;

import com.farizaghayev.factory.CommandFactory;
import com.farizaghayev.factory.Processor;
import com.farizaghayev.factory.AppCommandFactory;
import com.farizaghayev.service.TrainTownService;
import com.farizaghayev.service.TrainTownServiceImpl;
import com.farizaghayev.app.ServiceMap;
import com.farizaghayev.app.ServiceMapImpl;

import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) {

        if (args.length != 2) {

            System.err.println(Properties.getProperties().get("message.usage"));
            System.exit(1);

        } else {

            String graph = args[0];
            String commands = args[1];

            ServiceMap map = new ServiceMapImpl();


            try {

                map.init(graph);

                TrainTownService service = new TrainTownServiceImpl(map);

                CommandFactory commandFactory = new AppCommandFactory(service);

                Processor processor = new Processor(commandFactory);

                System.out.println(processor.runAll(commands));

            } catch (FileNotFoundException e) {

                e.printStackTrace();

            }
        }
    }
}

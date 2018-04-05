package com.farizaghayev.factory;

import com.farizaghayev.factory.commands.*;
import com.farizaghayev.service.TrainTownService;
import com.farizaghayev.Properties;

import java.util.Arrays;
import java.util.NoSuchElementException;


public class AppCommandFactory implements CommandFactory {

    private final String CMD_DISTANCE = "distance";

    private final String CMD_SHORTEST_PATH = "shortest_path";

    private final String CMD_DISTANCE_SHORTEST_PATH = "distance_shortest_path";

    private final String CMD_EDGE_MAX_STOPS = "count_egdes_max_stops";

    private final String CMD_EDGE_WITH_STOP = "count_edges_with_stops";

    private final String CMD_EDGE_MAX_DISTANCE = "count_edges_max_distance";

    private final TrainTownService service;


    public AppCommandFactory(TrainTownService service) {
        this.service = service;
    }


    public AbstractServiceCommand createCommand(String input) {
        String[] parts = input.split(";");

        if (parts.length <= 1) {
            throw new IllegalArgumentException(Properties.propertyWithArgs("message.service.command.parse_input_format", input));
        } else {
            String name = parts[0];

            switch (name) {
                case CMD_SHORTEST_PATH:
                    return createShortestPathCommand(input);
                case CMD_DISTANCE_SHORTEST_PATH:
                    return createShortestPathDistanceCommand(input);
                case CMD_DISTANCE:
                    return createDistanceCommand(input);
                case CMD_EDGE_MAX_STOPS:
                    return createCountEgdesMaxStopsCommand(input);
                case CMD_EDGE_WITH_STOP:
                    return createCountEdgesWithStopsCommand(input);
                case CMD_EDGE_MAX_DISTANCE:
                    return createCountEdgesMaxDistanceCommand(input);
                default:
                    throw new NoSuchElementException(Properties.propertyWithArgs("message.service.command.parse_input_unknown", name));
            }

        }
    }



    private ShortestPathCommand createShortestPathCommand(String input) {
        String[] parts = input.split(";");

        if (parts.length < 3 || parts.length > 3) {
            throw new IllegalArgumentException(Properties.propertyWithArgs("message.service.command.parse_input_format", input));
        } else {
            ShortestPathCommand command = new ShortestPathCommand(service);

            command.setStart(parts[1]);
            command.setDest(parts[2]);

            return command;
        }
    }


    private ShortestPathDistanceCommand createShortestPathDistanceCommand(String input) {
        String[] parts = input.split(";");

        if (parts.length < 3 || parts.length > 3) {
            throw new IllegalArgumentException(Properties.propertyWithArgs("message.service.command.parse_input_format", input));
        } else {
            ShortestPathDistanceCommand command = new ShortestPathDistanceCommand(service);

            command.setStart(parts[1]);
            command.setDest(parts[2]);

            return command;
        }
    }


    private DistanceCommand createDistanceCommand(String input) {
        String[] parts = input.split(";");

        if (parts.length < 2) {
            throw new IllegalArgumentException(Properties.propertyWithArgs("message.service.command.parse_input_format", input));
        } else {
            DistanceCommand command = new DistanceCommand(service);

            String[] townNames = Arrays.copyOfRange(parts, 1, parts.length);
            command.setNodeNames(townNames);

            return command;
        }
    }


    private CountEdgesMaxStopsCommand createCountEgdesMaxStopsCommand(String input) {
        String[] parts = input.split(";");

        if (parts.length < 4 || parts.length > 4) {
            throw new IllegalArgumentException(Properties.propertyWithArgs("message.service.command.parse_input_format", input));
        } else {
            CountEdgesMaxStopsCommand command = new CountEdgesMaxStopsCommand(service);

            command.setStart(parts[1]);
            command.setDest(parts[2]);

            try {
                command.setMaxStops(Integer.parseInt(parts[3]));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(Properties.propertyWithArgs("message.service.command.parse_input_format", input));
            }

            return command;
        }
    }


    private CountEdgesWithStopCommand createCountEdgesWithStopsCommand(String input) {
        String[] parts = input.split(";");

        if (parts.length < 4 || parts.length > 4) {
            throw new IllegalArgumentException(Properties.propertyWithArgs("message.service.command.parse_input_format", input));
        } else {
            CountEdgesWithStopCommand command = new CountEdgesWithStopCommand(service);

            command.setStart(parts[1]);
            command.setDest(parts[2]);

            try {
                command.setStops(Integer.parseInt(parts[3]));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(Properties.propertyWithArgs("message.service.command.parse_input_format", input));
            }

            return command;
        }
    }


    private CountEdgesMaxDistanceCommand createCountEdgesMaxDistanceCommand(String input) {
        String[] parts = input.split(";");

        if (parts.length < 4 || parts.length > 4) {
            throw new IllegalArgumentException(Properties.propertyWithArgs("message.service.command.parse_input_format", input));
        } else {
            CountEdgesMaxDistanceCommand command = new CountEdgesMaxDistanceCommand(service);

            command.setStart(parts[1]);
            command.setDest(parts[2]);

            try {
                command.setMaxDistance(Integer.parseInt(parts[3]));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(Properties.propertyWithArgs("message.service.command.parse_input_format", input));
            }

            return command;
        }
    }
}

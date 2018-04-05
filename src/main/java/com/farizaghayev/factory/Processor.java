package com.farizaghayev.factory;

import com.farizaghayev.Properties;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;


public class Processor {


    private CommandFactory commandFactory;


    public Processor(CommandFactory commandFactory) {
        this.commandFactory = commandFactory;
    }



    private List<Command> loadFile(String path) throws FileNotFoundException {
        List<Command> commands = new ArrayList<Command>();

        Scanner in = new Scanner(new FileInputStream(path));

        while (in.hasNext()) {
            String input = in.next();
            commands.add(commandFactory.createCommand(input));
        }

        return commands;
    }





    public String runAll(String path) throws FileNotFoundException {
        List<Command> commands = loadFile(path);

        if (!commands.isEmpty()) {
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < commands.size(); i++) {
                Command command = commands.get(i);
                sb.append(command.execute());
                sb.append("\n");
            }

            Command command = commands.get(commands.size() - 1);
            sb.append(command.execute());

            return sb.toString();
        } else {
            throw new NoSuchElementException(Properties.getProperties().get("message.command.no_such_element"));
        }
    }


    public String run(String input) {
        if (input != null) {
            try {
                Command command = commandFactory.createCommand(input);
                Object result = command.execute();
                return result.toString();
            } catch (NoSuchElementException e) {
                throw new IllegalArgumentException(Properties.propertyWithArgs("message.command.processor.parse_input_format", input));
            }
        } else {
            throw new IllegalArgumentException(Properties.propertyWithArgs("message.command.processor.parse_input_format", input));
        }
    }
}

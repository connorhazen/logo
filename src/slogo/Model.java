package slogo;

import exceptions.UnkownCommandException;

public class Model implements ModelInterface{
    private Parser parser;

    public Model(){
        parser = new Parser();
    }

    @Override
    public boolean runCommand(String input) throws UnkownCommandException {
        String[] parsedCommand = parser.parseCommand(input);
        return true;
    }
}
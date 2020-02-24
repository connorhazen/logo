package slogo;

import exceptions.UnkownCommandException;

public class Model implements ModelInterface{
    private Parser parser;

    public Model(){
        parser = new Parser();
    }

    @Override
    public Turtle runCommand(String input, Turtle turtle) throws UnkownCommandException {
        return turtle;
        //return parser.parseCommand(input);
    }
}

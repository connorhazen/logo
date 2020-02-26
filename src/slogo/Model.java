package slogo;

import slogo.exceptions.UnknownCommandException;

public class Model implements ModelInterface{
    private Parser parser;

    public Model(){
        parser = new Parser();
    }

    @Override
    public Turtle runCommand(String input, Turtle turtle) throws UnknownCommandException {
        turtle.setLocation(turtle.getX()+5, turtle.getY()+5);
        return turtle;
        //return parser.parseCommand(input);
    }
}
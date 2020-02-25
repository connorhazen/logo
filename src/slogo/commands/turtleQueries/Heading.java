package slogo.commands.turtleQueries;

import slogo.Turtle;

import java.util.List;

public class Heading extends TurtleQuery{

    public Heading(String text, List<String> args, Turtle toldTurtle) {
        super(text, args, toldTurtle);
        setMyNumArgs(0);
    }

    @Override
    protected double execute(Turtle executeOnTurtle) {
        return executeOnTurtle.getAngle();
    }
}

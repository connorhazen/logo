package slogo.commands;

import slogo.Turtle;

import java.util.List;

public class PenUp extends TurtleCommand{
    public PenUp(String text, List<String> args, Turtle toldTurtle) {
        super(text, args, toldTurtle);
        setMyNumArgs(0);
    }

    @Override
    protected double execute(Turtle executeOnTurtle) {
        executeOnTurtle.setPenStatus(false);
        return 0;
    }
}

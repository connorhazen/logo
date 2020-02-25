package slogo.commands.turtleCommands;

import slogo.Turtle;

import java.util.List;

public class PenDown extends TurtleCommand {
    public PenDown(String text, List<String> args, Turtle toldTurtle) {
        super(text, args, toldTurtle);
        setMyNumArgs(0);
    }

    @Override
    protected double execute(Turtle executeOnTurtle) {
        executeOnTurtle.setPenStatus(true);
        return 1;
    }
}

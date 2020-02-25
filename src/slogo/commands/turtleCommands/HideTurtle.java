package slogo.commands.turtleCommands;

import slogo.Turtle;

import java.util.List;

public class HideTurtle extends TurtleCommand{
    public HideTurtle(String text, List<String> args, Turtle toldTurtle) {
        super(text, args, toldTurtle);
        setMyNumArgs(0);
    }

    @Override
    protected double execute(Turtle executeOnTurtle) {
        executeOnTurtle.setVisibilityStatus(false);
        return 0;
    }
}

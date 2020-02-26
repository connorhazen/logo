package slogo.commands;

import slogo.Turtle;

import java.util.List;

public class ShowTurtle extends TurtleCommand{
    public ShowTurtle(String text, List<String> args, Turtle toldTurtle) {
        super(text, args, toldTurtle);
        setMyNumArgs(0);
    }

    @Override
    protected double execute(Turtle executeOnTurtle) {
        executeOnTurtle.setVisibilityStatus(true);
        return 1;
    }
}

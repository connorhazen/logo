package slogo.commands;

import slogo.Turtle;

import java.util.List;

public class Backward extends TurtleCommand {
    public Backward(String text, List<String> args, Turtle toldTurtle) {
        super(text, args, toldTurtle);
        setMyNumArgs(1);
    }

    @Override
    protected double execute(Turtle executeOnTurtle) {
        double distance = getArgsDouble().get(0);
        move(-distance, executeOnTurtle);
        return distance;
    }
}

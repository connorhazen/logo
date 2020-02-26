package slogo.commands;

import slogo.Turtle;

import java.util.List;

public class Right extends TurtleCommand {
    public Right(String text, List<String> args, Turtle toldTurtle) {
        super(text, args, toldTurtle);
        setMyNumArgs(1);
    }

    @Override
    protected double execute(Turtle executeOnTurtle) {
        double degree = getArgsDouble().get(0);
        turn(degree, executeOnTurtle);
        return degree;
    }
}

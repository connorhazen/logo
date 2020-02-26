package slogo.commands;

import slogo.Turtle;

import java.util.List;

public class Tan extends MathOperation {
    public Tan(String text, List<String> args, Turtle toldTurtle) {
        super(text, args, toldTurtle);
        setMyNumArgs(1);
    }

    @Override
    protected double execute(Turtle executeOnTurtle) {
        return Math.tan(Math.toRadians(getArgsDouble().get(0)));
    }
}

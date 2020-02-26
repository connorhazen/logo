package slogo.commands;

import slogo.Turtle;

import java.util.List;

public class Cos extends MathOperation {
    public Cos(String text, List<String> args, Turtle toldTurtle) {
        super(text, args, toldTurtle);
        setMyNumArgs(1);
    }

    @Override
    protected double execute(Turtle executeOnTurtle) {
        return Math.cos(Math.toRadians(getArgsDouble().get(0)));
    }
}

package slogo.commands;

import slogo.Turtle;

import java.util.List;

public class Pi extends MathOperation {
    public Pi(String text, List<String> args, Turtle toldTurtle) {
        super(text, args, toldTurtle);
        setMyNumArgs(0);
    }

    @Override
    protected double execute(Turtle executeOnTurtle) {
        return Math.PI;
    }
}

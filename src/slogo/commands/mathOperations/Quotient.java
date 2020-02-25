package slogo.commands.mathOperations;

import slogo.Turtle;

import java.util.List;

public class Quotient extends MathOperation {
    public Quotient(String text, List<String> args, Turtle toldTurtle) {
        super(text, args, toldTurtle);
        setMyNumArgs(2);
    }

    @Override
    protected double execute(Turtle executeOnTurtle) {
        return getArgsDouble().get(0) / getArgsDouble().get(1);
    }
}
package slogo.commands;

import slogo.Turtle;

import java.util.List;

public class Minus extends MathOperation {
    public Minus(String text, List<String> args, Turtle toldTurtle) {
        super(text, args, toldTurtle);
        setMyNumArgs(1);
    }

    @Override
    protected double execute(Turtle executeOnTurtle) {
        return -getArgsDouble().get(0);
    }
}

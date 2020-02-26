package slogo.commands;

import slogo.Turtle;

import java.util.List;

public class Log extends MathOperation {
    public Log(String text, List<String> args, Turtle toldTurtle) {
        super(text, args, toldTurtle);
        setMyNumArgs(1);
    }

    @Override
    protected double execute(Turtle executeOnTurtle) {
        return Math.log(getArgsDouble().get(0));
    }
}

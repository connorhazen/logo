package slogo.commands;

import slogo.structs.CommandStruct;
import slogo.Turtle;

import java.util.List;

public class Cosine extends MathOperation {
    public Cosine(CommandStruct commandStruct, String text, List<String> args, Turtle toldTurtle) {
        super(commandStruct, text, args, toldTurtle);
        setMyNumArgs(1);
    }

    @Override
    protected double execute(Turtle executeOnTurtle) {
        return Math.cos(Math.toRadians(getArgsDouble().get(0)));
    }
}

package slogo.commands;

import slogo.structs.CommandStruct;
import slogo.Turtle;

import java.util.List;

public class Tan extends MathOperation {
    public Tan(CommandStruct commandStruct, String text, List<String> args, Turtle toldTurtle) {
        super(commandStruct, text, args, toldTurtle);
        setMyNumArgs(1);
    }

    @Override
    protected double execute(Turtle executeOnTurtle) {
        return Math.tan(Math.toRadians(getArgsDouble().get(0)));
    }
}

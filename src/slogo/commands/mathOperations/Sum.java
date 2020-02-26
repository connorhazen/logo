package slogo.commands.mathOperations;

import slogo.structs.CommandStruct;
import slogo.Turtle;

import java.util.List;

public class Sum extends MathOperation {
    public Sum(CommandStruct commandStruct, String text, List<String> args, Turtle toldTurtle) {
        super(commandStruct, text, args, toldTurtle);
        setMyNumArgs(2);
    }

    @Override
    protected double execute(Turtle executeOnTurtle) {
        return getArgsDouble().get(0) + getArgsDouble().get(1);
    }
}

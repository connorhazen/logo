package slogo.commands.mathOperations;

import slogo.structs.CommandStruct;
import slogo.Turtle;

import java.util.List;

public class ATan extends MathOperation{

    public ATan(CommandStruct commandStruct, String text, List<String> args, Turtle toldTurtle) {
        super(commandStruct, text, args, toldTurtle);
        setMyNumArgs(1);
    }

    @Override
    protected double execute(Turtle executeOnTurtle) {
       return Math.toDegrees(Math.atan(getArgsDouble().get(0)));
    }
}

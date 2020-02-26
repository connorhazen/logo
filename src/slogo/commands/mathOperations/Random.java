package slogo.commands.mathOperations;

import slogo.structs.CommandStruct;
import slogo.Turtle;

import java.util.List;

public class Random extends MathOperation {
    public Random(CommandStruct commandStruct, String text, List<String> args, Turtle toldTurtle) {
        super(commandStruct, text, args, toldTurtle);
        setMyNumArgs(1);
    }

    @Override
    protected double execute(Turtle executeOnTurtle) {
        //TODO: implement exception checking for when input is negative
        return Math.random() * getArgsDouble().get(0);
    }
}

package slogo.commands;

import slogo.structs.CommandStruct;
import slogo.Turtle;

import java.util.List;

public class ArcTangent extends MathOperation{

    public ArcTangent(CommandStruct commandStruct, String text, List<String> args, Turtle toldTurtle) {
        super(commandStruct, text, args, toldTurtle);
        setMyNumArgs(1);
    }

    @Override
    protected double execute(Turtle executeOnTurtle) {
       return Math.toDegrees(Math.atan(getArgsDouble().get(0)));
    }
}

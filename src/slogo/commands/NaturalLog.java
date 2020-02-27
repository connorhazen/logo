package slogo.commands;

import slogo.structs.CommandStruct;
import slogo.view.Turtle;

import java.util.List;

public class NaturalLog extends MathOperation {
    public NaturalLog(CommandStruct commandStruct, String text, List<String> args, Turtle toldTurtle) {
        super(commandStruct, text, args, toldTurtle);
        setMyNumArgs(1);
    }

    @Override
    protected double execute(Turtle executeOnTurtle) {
        return Math.log(getArgsDouble().get(0));
    }
}

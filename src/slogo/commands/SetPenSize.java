package slogo.commands;

import slogo.exceptions.InvalidParameterException;
import slogo.exceptions.UnknownCommandException;
import slogo.structs.CommandStruct;
import slogo.view.Turtle;

import java.util.List;

public class SetPenSize extends TurtleCommand {
    public SetPenSize(CommandStruct commandStruct, String text, List<String> args, Turtle toldTurtle) {
        super(commandStruct, text, args, toldTurtle);
        setMyNumArgs(1);
    }

    @Override
    protected double execute(Turtle executeOnTurtle) throws UnknownCommandException, InvalidParameterException {
        int index = (int) Math.round(getArgsDouble().get(0));
        executeOnTurtle.getPen().setSize(index);
        getCommandStruct().setPenSize(index);
        return index;
    }
}

package slogo.commands;

import slogo.exceptions.InvalidParameterException;
import slogo.exceptions.UnknownCommandException;
import slogo.structs.CommandStruct;
import slogo.view.Turtle;

import java.util.List;

public class SetPalette extends Misc {
    public SetPalette(CommandStruct commandStruct, String text, List<String> args, Turtle toldTurtle) {
        super(commandStruct, text, args, toldTurtle);
        setMyNumArgs(4);
    }

    @Override
    protected double execute(Turtle executeOnTurtle) throws UnknownCommandException, InvalidParameterException {
        int index = (int) Math.round(getArgsDouble().get(0));
        int r = (int) Math.round(getArgsDouble().get(1));
        int g = (int) Math.round(getArgsDouble().get(2));
        int b = (int) Math.round(getArgsDouble().get(3));

        this.getCommandStruct().setColor(index, r, g, b);

        return index;

    }
}

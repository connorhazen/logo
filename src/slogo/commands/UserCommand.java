package slogo.commands;

import slogo.exceptions.InvalidParameterException;
import slogo.exceptions.UnknownCommandException;
import slogo.structs.CommandStruct;
import slogo.view.Turtle;

import java.util.List;

public class UserCommand extends BasicSyntax {
    public UserCommand(CommandStruct commandStruct, String text, List<String> args, Turtle toldTurtle) {
        super(commandStruct, text, args, toldTurtle);
    }

    @Override
    protected double execute(Turtle executeOnTurtle) throws UnknownCommandException, InvalidParameterException {
        return 0;
    }
}

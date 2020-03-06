package slogo.commands;

import slogo.exceptions.InvalidParameterException;
import slogo.exceptions.UnknownCommandException;
import slogo.structs.CommandStruct;
import slogo.structs.UserCommandStruct;
import slogo.view.Turtle;

import java.util.List;

public class To extends Misc{
    public To(CommandStruct commandStruct, String text, List<String> args, Turtle toldTurtle) {
        super(commandStruct, text, args, toldTurtle);
        setMyNumArgs(1);
    }

    @Override
    protected double execute(Turtle executeOnTurtle) throws UnknownCommandException, InvalidParameterException {
        String commandName = getArgString(0);
        String varString = getListString1();
        List<String> varList = parseStringIntoVar(varString);


        UserCommandStruct command = new UserCommandStruct(commandName, getListString2(), varList);
        getCommandStruct().addUserCommand(command); //ToDo: error handeling

        return 1;
    }
}

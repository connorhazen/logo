package slogo.commands;

import slogo.exceptions.InvalidParameterException;
import slogo.exceptions.UnknownCommandException;
import slogo.structs.CommandStruct;
import slogo.structs.UserCommandStruct;
import slogo.structs.VariableStruct;
import slogo.view.Turtle;

import java.util.List;

public class UserCommand extends BasicSyntax {


    public UserCommand(CommandStruct commandStruct, String text, List<String> args, Turtle toldTurtle) {
        super(commandStruct, text, args, toldTurtle);
        getCommandStruct().containsUserCommand(firstWord(text)); //throw and catch exception TODO
        setMyNumArgs(getCommandStruct().getUserCommand(firstWord(text)).getArgs().size());
    }

    @Override
    protected double execute(Turtle executeOnTurtle) throws UnknownCommandException, InvalidParameterException {
        String commandName = firstWord(getMyText());
        List<Double> doubleArgs = getArgsDouble();
        List<String> varNames = getCommandStruct().getUserCommand(commandName).getArgs();
        for(int i = 0; i < getNumArgs(); i++){
            VariableStruct varArg = new VariableStruct(varNames.get(i), doubleArgs.get(i));
            getCommandStruct().addVariable(varArg);
        }
        UserCommandStruct command = getCommandStruct().getUserCommand(commandName);
        String executableString = command.getCommandInput();
        getCommandStruct().getModel().runCommand(executableString, executeOnTurtle);
        return retVal(executableString);
    }
}

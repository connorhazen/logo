package slogo.commands;

import slogo.exceptions.InvalidParameterException;
import slogo.exceptions.UnknownCommandException;
import slogo.structs.CommandStruct;
import slogo.structs.VariableStruct;
import slogo.view.Turtle;

import java.util.List;

public class MakeVariable extends Misc {
    public MakeVariable(CommandStruct commandStruct, String text, List<String> args, Turtle toldTurtle) {
        super(commandStruct, text, args, toldTurtle);
        setMyNumArgs(2);
    }

    @Override
    protected double execute(Turtle executeOnTurtle) throws UnknownCommandException, InvalidParameterException {
        String var = getArgString(0);
        double val = Double.parseDouble(getArgString(1));

        //accessing the variable map
        CommandStruct commandStruct = getCommandStruct();
        VariableStruct insertStruct = new VariableStruct(var, val);
        commandStruct.addVariable(insertStruct);

        return val;
    }
}

package slogo.commands;

import slogo.exceptions.InvalidParameterException;
import slogo.exceptions.UnknownCommandException;
import slogo.structs.CommandStruct;
import slogo.structs.Struct;
import slogo.structs.VariableStruct;
import slogo.view.Turtle;

import java.util.List;

public class Make extends Misc {
    public Make(CommandStruct commandStruct, String text, List<String> args, Turtle toldTurtle) {
        super(commandStruct, text, args, toldTurtle);
        setMyNumArgs(2);
    }

    @Override
    protected double execute(Turtle executeOnTurtle) throws UnknownCommandException, InvalidParameterException {
        String var = getArgString(0);
        double val = getArgsDouble().get(1);  //verify

        //accessing the variable map
        CommandStruct commandStruct = getCommandStruct();
        VariableStruct insertStruct = new VariableStruct(var, val);
        commandStruct.addVariable(insertStruct);

        return val;
    }
}

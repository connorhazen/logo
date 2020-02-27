package slogo.commands;

import slogo.Turtle;
import slogo.exceptions.InvalidParameterException;
import slogo.exceptions.UnknownCommandException;
import slogo.structs.CommandStruct;

import java.util.List;

public class IfElse extends Misc {
    public IfElse(CommandStruct commandStruct, String text, List<String> args, Turtle toldTurtle) {
        super(commandStruct, text, args, toldTurtle);
        setMyNumArgs(1);
    }

    @Override
    protected double execute(Turtle executeOnTurtle) throws UnknownCommandException, InvalidParameterException {
        double ret = 0;

        if(getArgsDouble().get(0) != 0){
            getCommandStruct().getModel().runCommand(getListString1(), executeOnTurtle);
            if(getListString1().length() > 1){  // TODO: change to not hardcode
                ret = lastRetVal(getListString1());
            }

        }

        return ret;
    }
}
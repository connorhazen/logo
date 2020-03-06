package slogo.commands;

import slogo.view.Turtle;
import slogo.exceptions.InvalidParameterException;
import slogo.exceptions.UnknownCommandException;
import slogo.structs.CommandStruct;

import java.util.List;

public class IfElse extends Misc {
    public IfElse(CommandStruct commandStruct, String text, List<String> args, Turtle toldTurtle) {
        super(commandStruct, text, args, toldTurtle);
        setMyNumArgs(0);
    }

    @Override
    protected double execute(Turtle executeOnTurtle) throws UnknownCommandException, InvalidParameterException {
        double ret = 0;

        if(retVal(getExpression()) != 0){ //TODO: refactor
            getCommandStruct().getModel().runCommand(getListString1(), executeOnTurtle);
            if(getListString1().length() > 1){  // TODO: change to not hardcode
                ret = retVal(getListString1());
            }
        }else{
            getCommandStruct().getModel().runCommand(getListString2(), executeOnTurtle);
            if(getListString2().length() > 1){  // TODO: change to not hardcode
                ret = retVal(getListString2());
            }
        }

        return ret;
    }
}

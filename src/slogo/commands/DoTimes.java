package slogo.commands;

import slogo.exceptions.InvalidParameterException;
import slogo.exceptions.UnknownCommandException;
import slogo.structs.CommandStruct;
import slogo.structs.VariableStruct;
import slogo.view.Turtle;

import java.util.List;

public class DoTimes extends Misc {
    public DoTimes(CommandStruct commandStruct, String text, List<String> args, Turtle toldTurtle) {
        super(commandStruct, text, args, toldTurtle);
        setMyNumArgs(0);
    }

    @Override
    protected double execute(Turtle executeOnTurtle) throws UnknownCommandException, InvalidParameterException {
        double ret = 0;

        List<Double> loopConstants = getLoopConstants();
        double limit = loopConstants.get(0);
        String varName = parseStringIntoVar(getListString1()).get(0);

        for(double i = 1; i <= limit; i ++){
            VariableStruct itterator = new VariableStruct(varName, i);
            getCommandStruct().addVariable(itterator);
            getCommandStruct().getModel().runCommand(getListString1(), executeOnTurtle);

            if(i > limit ){
                ret = retVal(getListString1());
            }
        }
        return ret;
    }
}

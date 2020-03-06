package slogo.commands;

import slogo.exceptions.InvalidParameterException;
import slogo.exceptions.UnknownCommandException;
import slogo.structs.CommandStruct;
import slogo.view.Turtle;

import java.util.HashSet;
import java.util.List;

public class Ask extends Misc{
    public Ask(CommandStruct commandStruct, String text, List<String> args, Turtle toldTurtle) {
        super(commandStruct, text, args, toldTurtle);
        setMyNumArgs(0);
    }

    @Override
    protected double execute(Turtle executeOnTurtle) throws UnknownCommandException, InvalidParameterException {
        List<Double> idList = getLoopConstants();
        HashSet<Turtle> executeOnTurtlesSet = new HashSet<>();

        for(double id : idList){
            int intID = (int) Math.round(id);
            executeOnTurtlesSet.add(getCommandStruct().getTurtle(intID));
        }

        getCommandStruct().setTurtleSet(executeOnTurtlesSet);

        getCommandStruct().getModel().runCommand(getListString2(), executeOnTurtle);
        double ret = retVal(getListString2());

        getCommandStruct().setTurtleSetDefault();

        return ret;
    }
}

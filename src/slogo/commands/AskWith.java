package slogo.commands;

import slogo.exceptions.InvalidParameterException;
import slogo.exceptions.UnknownCommandException;
import slogo.structs.CommandStruct;
import slogo.view.Turtle;

import java.util.HashSet;
import java.util.List;

public class AskWith extends Misc {
    public AskWith(CommandStruct commandStruct, String text, List<String> args, Turtle toldTurtle) {
        super(commandStruct, text, args, toldTurtle);
        setMyNumArgs(0);
    }

    @Override
    protected double execute(Turtle executeOnTurtle) throws UnknownCommandException, InvalidParameterException {
        HashSet<Turtle> executeOnTurtleSet = new HashSet<>();

        for(Turtle turtle : getCommandStruct().getTurtleSet()){
            HashSet<Turtle> temp = new HashSet<>();
            temp.add(turtle);
            getCommandStruct().setTurtleSet(temp);
            double conditional = retVal(getListString1());
            if(conditional != 0){
                executeOnTurtleSet.add(turtle);
            }
        }

        getCommandStruct().setTurtleSet(executeOnTurtleSet);

        getCommandStruct().getModel().runCommand(getListString2(), executeOnTurtle);
        double ret = retVal(getListString2());

        getCommandStruct().setTurtleSetDefault();

        return ret;
    }
}

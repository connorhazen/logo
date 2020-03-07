package slogo.commands;

import slogo.exceptions.InvalidParameterException;
import slogo.exceptions.UnknownCommandException;
import slogo.structs.CommandStruct;
import slogo.view.Turtle;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Tell extends Misc {
    public Tell(CommandStruct commandStruct, String text, List<String> args, Turtle toldTurtle) {
        super(commandStruct, text, args, toldTurtle);
        setMyNumArgs(0);
    }

    @Override
    protected double execute(Turtle executeOnTurtle) throws UnknownCommandException, InvalidParameterException {
        List<Double> idList = getLoopConstants();
        Set<Turtle> executeOnTurtleSet = new HashSet<>();


        for(Double turtleID : idList){
            int id = (int) Math.round(turtleID);
            Turtle currentTurtle = getCommandStruct().getTurtle(id);
            executeOnTurtleSet.add(currentTurtle);
        }

        getCommandStruct().setTurtleSet(executeOnTurtleSet);

        return idList.get(idList.size()-1);
    }
}

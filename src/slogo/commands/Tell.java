package slogo.commands;

import slogo.exceptions.InvalidParameterException;
import slogo.exceptions.UnknownCommandException;
import slogo.structs.CommandStruct;
import slogo.view.Turtle;

import java.util.List;

public class Tell extends Misc {
    public Tell(CommandStruct commandStruct, String text, List<String> args, Turtle toldTurtle) {
        super(commandStruct, text, args, toldTurtle);
        setMyNumArgs(0);
    }

    @Override
    protected double execute(Turtle executeOnTurtle) throws UnknownCommandException, InvalidParameterException {
        List<Double> idList = getLoopConstants();
        for(Double turtleID : idList){
            int id = (int) Math.round(turtleID);
            getCommandStruct().addTurtle(new Turtle(id,0,0,-90)); //make with properties TODO
        }
        return idList.get(idList.size()-1);
    }
}

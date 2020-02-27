package slogo.commands;

import slogo.structs.CommandStruct;
import slogo.view.Turtle;

import java.util.List;

public class IsPenDown extends TurtleQuery {
    public IsPenDown(CommandStruct commandStruct, String text, List<String> args, Turtle toldTurtle) {
        super(commandStruct, text, args, toldTurtle);
        setMyNumArgs(0);
    }

    @Override
    protected double execute(Turtle executeOnTurtle) {
        if(executeOnTurtle.getPenStatus()){
            return 1;
        }
        return 0;
    }
}

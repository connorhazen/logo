package slogo.commands;

import slogo.structs.CommandStruct;
import slogo.Turtle;

import java.util.List;

public class ShowingQ extends TurtleQuery {
    public ShowingQ(CommandStruct commandStruct, String text, List<String> args, Turtle toldTurtle) {
        super(commandStruct, text, args, toldTurtle);
        setMyNumArgs(0);
    }

    @Override
    protected double execute(Turtle executeOnTurtle) {
        if(executeOnTurtle.getVisibilityStatus()){
            return 1;
        }
        return 0;
    }
}
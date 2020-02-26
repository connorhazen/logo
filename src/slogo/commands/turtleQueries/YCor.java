package slogo.commands.turtleQueries;

import slogo.structs.CommandStruct;
import slogo.Turtle;

import java.util.List;

public class YCor extends TurtleQuery{
    public YCor(CommandStruct commandStruct, String text, List<String> args, Turtle toldTurtle) {
        super(commandStruct, text, args, toldTurtle);
        setMyNumArgs(0);
    }

    @Override
    protected double execute(Turtle executeOnTurtle) {
        return executeOnTurtle.getY();
    }
}

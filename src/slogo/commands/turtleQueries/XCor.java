package slogo.commands.turtleQueries;

import slogo.structs.CommandStruct;
import slogo.Turtle;

import java.util.List;

public class XCor extends TurtleQuery{
    public XCor(CommandStruct commandStruct, String text, List<String> args, Turtle toldTurtle) {
        super(commandStruct, text, args, toldTurtle);
        setMyNumArgs(0);
    }

    @Override
    protected double execute(Turtle executeOnTurtle) {
        return executeOnTurtle.getX();
    }
}

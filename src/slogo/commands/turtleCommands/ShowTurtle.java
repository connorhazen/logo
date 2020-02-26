package slogo.commands.turtleCommands;

import slogo.structs.CommandStruct;
import slogo.Turtle;

import java.util.List;

public class ShowTurtle extends TurtleCommand{
    public ShowTurtle(CommandStruct commandStruct, String text, List<String> args, Turtle toldTurtle) {
        super(commandStruct, text, args, toldTurtle);
        setMyNumArgs(0);
    }

    @Override
    protected double execute(Turtle executeOnTurtle) {
        executeOnTurtle.setVisibilityStatus(true);
        return 1;
    }
}

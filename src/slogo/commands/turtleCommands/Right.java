package slogo.commands.turtleCommands;

import slogo.structs.CommandStruct;
import slogo.Turtle;

import java.util.List;

public class Right extends TurtleCommand {
    public Right(CommandStruct commandStruct, String text, List<String> args, Turtle toldTurtle) {
        super(commandStruct, text, args, toldTurtle);
        setMyNumArgs(1);
    }

    @Override
    protected double execute(Turtle executeOnTurtle) {
        double degree = getArgsDouble().get(0);
        turn(degree, executeOnTurtle);
        return degree;
    }
}

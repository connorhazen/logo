package slogo.commands.turtleCommands;

import slogo.structs.CommandStruct;
import slogo.Turtle;

import java.util.List;

public class Forward extends TurtleCommand{

    public Forward(CommandStruct commandStruct, String text, List<String> args, Turtle toldTurtle) {
        super(commandStruct, text, args, toldTurtle);
        setMyNumArgs(1);
    }

    @Override
    protected double execute(Turtle executeOnTurtle) {
        double distance = getArgsDouble().get(0);
        move(distance, executeOnTurtle);
        return distance;
    }


}

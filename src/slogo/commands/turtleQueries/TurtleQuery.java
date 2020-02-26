package slogo.commands.turtleQueries;

import slogo.structs.CommandStruct;
import slogo.Turtle;
import slogo.commands.Command;
import slogo.commands.TurtleSpecificCommand;

import java.util.List;

public abstract class TurtleQuery extends Command implements TurtleSpecificCommand {

    public TurtleQuery(CommandStruct commandStruct, String text, List<String> args, Turtle toldTurtle) {
        super(commandStruct, text, args, toldTurtle);
    }
}


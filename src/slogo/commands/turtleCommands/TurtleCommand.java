package slogo.commands.turtleCommands;

import slogo.Turtle;
import slogo.commands.Command;

import java.util.List;

public abstract class TurtleCommand extends Command {

    public TurtleCommand(String text, List<String> args, List<Turtle> toldTurtles) {
        super(text, args, toldTurtles);
    }
    
}

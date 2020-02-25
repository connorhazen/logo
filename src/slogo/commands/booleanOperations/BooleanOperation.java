package slogo.commands.booleanOperations;

import slogo.Turtle;
import slogo.commands.Command;

import java.util.List;

//simply used to be able to classify instances of commands

public abstract class BooleanOperation extends Command {
    public BooleanOperation(String text, List<String> args, Turtle toldTurtle) {
        super(text, args, toldTurtle);
    }
}

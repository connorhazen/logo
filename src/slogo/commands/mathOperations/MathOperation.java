package slogo.commands.mathOperations;

import slogo.Turtle;
import slogo.commands.Command;

import java.util.List;

public abstract class MathOperation extends Command {
    public MathOperation(String text, List<String> args, Turtle toldTurtle) {
        super(text, args, toldTurtle);
    }
}

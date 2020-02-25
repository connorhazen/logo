package slogo.commands.basicSyntax;

import slogo.Turtle;
import slogo.commands.Command;

import java.util.List;

public abstract class BasicSyntax extends Command {
    public BasicSyntax(String text, List<String> args, Turtle toldTurtle) {
        super(text, args, toldTurtle);
    }

    @Override
    public double execute(Turtle toldTurtle) {
        return Double.parseDouble(getMyText());
    }
}

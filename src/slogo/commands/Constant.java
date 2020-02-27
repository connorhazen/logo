package slogo.commands;

import slogo.structs.CommandStruct;
import slogo.view.Turtle;

import java.util.List;

public class Constant extends BasicSyntax {
    public Constant(CommandStruct commandStruct, String text, List<String> args, Turtle toldTurtle) {
        super(commandStruct, text, args, toldTurtle);
        setMyNumArgs(0);
    }

    @Override
    public double execute(Turtle toldTurtle) {
        return Double.parseDouble(getMyText());
    }

}

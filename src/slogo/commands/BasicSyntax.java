package slogo.commands;

import slogo.structs.CommandStruct;
import slogo.view.Turtle;

import java.util.List;

public abstract class BasicSyntax extends Command {
    public BasicSyntax(CommandStruct commandStruct, String text, List<String> args, Turtle toldTurtle) {
        super(commandStruct, text, args, toldTurtle);
    }


}

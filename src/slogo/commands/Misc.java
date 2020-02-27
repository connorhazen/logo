package slogo.commands;

import slogo.view.Turtle;
import slogo.structs.CommandStruct;

import java.util.List;

//TODO: implement looping mechanism
public abstract class Misc extends Command {

    public Misc(CommandStruct commandStruct, String text, List<String> args, Turtle toldTurtle) {
        super(commandStruct, text, args, toldTurtle);
    }



}

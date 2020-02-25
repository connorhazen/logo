package slogo.commands.misc;

import slogo.Turtle;
import slogo.commands.Command;

import java.util.List;

//TODO: implement looping mechanism
public abstract class Misc extends Command {

    public Misc(String text, List<String> args, Turtle toldTurtle) {
        super(text, args, toldTurtle);
    }



}

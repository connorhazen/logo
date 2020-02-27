package slogo.commands;

import slogo.view.Turtle;
import slogo.structs.CommandStruct;

import java.util.List;


//TODO: implement execute
public class For extends Misc{

    public For(CommandStruct commandStruct, String text, List<String> args, Turtle toldTurtle) {
        super(commandStruct, text, args, toldTurtle);
    }

    @Override
    protected double execute(Turtle executeOnTurtle) {
        return 0;
    }


}

package slogo.commands;

import slogo.Turtle;
import slogo.structs.CommandStruct;

import java.util.List;

public class Repeat extends Misc {


    public Repeat(CommandStruct commandStruct, String text, List<String> args, Turtle toldTurtle) {
        super(commandStruct, text, args, toldTurtle);
        setMyNumArgs(1);
    }

    @Override
    protected double execute(Turtle executeOnTurtle) {
        double ret = 0;
        double runNumTimes = getArgsDouble().get(0);

        for(int i = 0; i < runNumTimes; i++){
            ret = getCommandStruct().getModel().runCommand(getListString1(), executeOnTurtle);
        }

        return ret;
    }


}

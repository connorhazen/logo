package slogo.commands;

import slogo.structs.CommandStruct;
import slogo.view.Turtle;

import java.util.List;

public class Not extends BooleanOperation {
    public Not(CommandStruct commandStruct, String text, List<String> args, Turtle toldTurtle) {
        super(commandStruct, text, args, toldTurtle);
        setMyNumArgs(1);
    }

    //returns 1 if true, 0 if false
    //check to see if the arguments are equal
    @Override
    protected double execute(Turtle nullInput) {
        Double zero = 0.0;
        if(zero.equals(getArgsDouble().get(0)) ){
            return 1;
        }
        return 0;
    }


}

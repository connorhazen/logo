package slogo.commands;

import slogo.structs.CommandStruct;
import slogo.Turtle;

import java.util.List;

public class Or extends BooleanOperation {

    public Or(CommandStruct commandStruct, String text, List<String> args, Turtle toldTurtle) {
        super(commandStruct, text, args, toldTurtle);
        setMyNumArgs(2);
    }

    //returns 1 if true, 0 if false
    //check to see if at least one of the arguments is nonzero
    @Override
    protected double execute(Turtle nullInput) {
        Double zero = 0.0;
        if(zero.equals(getArgsDouble().get(0)) && zero.equals(getArgsDouble().get(1))){ //both are 0 return false aka 0
            return 0;
        }

        return 1;
    }

}

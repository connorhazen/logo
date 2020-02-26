package slogo.commands.booleanOperations;

import slogo.structs.CommandStruct;
import slogo.Turtle;

import java.util.List;

public class NotEqual extends BooleanOperation {
    public NotEqual(CommandStruct commandStruct, String text, List<String> args, Turtle toldTurtle) {
        super(commandStruct, text, args, toldTurtle);
        setMyNumArgs(2);
    }

    //returns 1 if true, 0 if false
    //check to see if the arguments are not equal
    @Override
    protected double execute(Turtle nullInput) {

        if(getArgsDouble().get(0).equals(getArgsDouble().get(1)) ){
            return 0;
        }
        return 1;
    }
}

package slogo.commands;

import slogo.Turtle;

import java.util.List;

public class NotEqual extends BooleanOperation {
    public NotEqual(String text, List<String> args, Turtle toldTurtle) {
        super(text, args, toldTurtle);
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

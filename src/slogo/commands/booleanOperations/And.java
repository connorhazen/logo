package slogo.commands.booleanOperations;

import slogo.Turtle;

import java.util.List;

public class And extends BooleanOperation{
    public And(String text, List<String> args, Turtle toldTurtle) {
        super(text, args, toldTurtle);
        setMyNumArgs(2);
    }

    //returns 1 if true, 0 if false
    //check to see if both arguments are nonzero
    @Override
    protected double execute(Turtle nullInput) {
        Double zero = 0.0;
        if(zero.equals(getArgsDouble().get(0)) || zero.equals(getArgsDouble().get(1))){ //both are 0 return false aka 0
            return 0;
        }

        return 1;
    }
}

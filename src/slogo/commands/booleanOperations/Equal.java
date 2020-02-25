package slogo.commands.booleanOperations;

import slogo.Turtle;

import java.util.List;

public class Equal extends BooleanOperation{

    public Equal(String text, List<String> args, Turtle toldTurtle) {
        super(text, args, toldTurtle);
        setMyNumArgs(2);
    }

    //returns 1 if true, 0 if false
    //check to see if the arguments are equal
    @Override
    protected double execute(Turtle nullInput) {

        if(getArgsDouble().get(0).equals(getArgsDouble().get(1)) ){
            return 1;
        }
        return 0;
    }
}

package slogo.commands;

import slogo.Turtle;

import java.util.List;

public class GreaterThan extends  BooleanOperation{
    public GreaterThan(String text, List<String> args, Turtle toldTurtle) {
        super(text, args, toldTurtle);
        setMyNumArgs(2);
    }

    @Override
    protected double execute(Turtle nullInput) {
        if(getArgsDouble().get(0) > getArgsDouble().get(1)){ //both are 0 return false aka 0
            return 1;
        }

        return 0;
    }
}

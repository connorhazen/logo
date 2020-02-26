package slogo.commands;

import slogo.Turtle;

import java.util.List;

public class ShowingQ extends TurtleQuery {
    public ShowingQ(String text, List<String> args, Turtle toldTurtle) {
        super(text, args, toldTurtle);
        setMyNumArgs(0);
    }

    @Override
    protected double execute(Turtle executeOnTurtle) {
        if(executeOnTurtle.getVisibilityStatus()){
            return 1;
        }
        return 0;
    }
}

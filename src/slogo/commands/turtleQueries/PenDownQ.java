package slogo.commands.turtleQueries;

import slogo.Turtle;

import java.util.List;

public class PenDownQ extends TurtleQuery {
    public PenDownQ(String text, List<String> args, Turtle toldTurtle) {
        super(text, args, toldTurtle);
        setMyNumArgs(0);
    }

    @Override
    protected double execute(Turtle executeOnTurtle) {
        if(executeOnTurtle.getPenStatus()){
            return 1;
        }
        return 0;
    }
}

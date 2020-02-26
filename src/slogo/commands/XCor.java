package slogo.commands;

import slogo.Turtle;

import java.util.List;

public class XCor extends TurtleQuery{
    public XCor(String text, List<String> args, Turtle toldTurtle) {
        super(text, args, toldTurtle);
        setMyNumArgs(0);
    }

    @Override
    protected double execute(Turtle executeOnTurtle) {
        return executeOnTurtle.getX();
    }
}

package slogo.commands.turtleCommands;

import slogo.Turtle;

import java.util.List;

public class Home extends TurtleCommand {
    public Home(String text, List<String> args, Turtle toldTurtle) {
        super(text, args, toldTurtle);
        setMyNumArgs(0);
    }

    @Override
    protected double execute(Turtle executeOnTurtle) {
        double xCur = executeOnTurtle.getX();
        double yCur = executeOnTurtle.getY();

        moveToLoc(0, 0, executeOnTurtle);

        double distance = Math.sqrt(Math.pow(xCur, 2) + Math.pow(yCur, 2));

        return distance;
    }
}

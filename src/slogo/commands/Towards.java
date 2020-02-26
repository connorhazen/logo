package slogo.commands;

import slogo.Turtle;

import java.util.List;

public class Towards extends TurtleCommand {
    public Towards(String text, List<String> args, Turtle toldTurtle) {
        super(text, args, toldTurtle);
        setMyNumArgs(2);
    }

    @Override
    protected double execute(Turtle executeOnTurtle) {
        double curX = executeOnTurtle.getX();
        double curY = executeOnTurtle.getY();
        double x = getArgsDouble().get(0);
        double y = getArgsDouble().get(1);

        double curAngle = executeOnTurtle.getAngle();

        double newAngle = Math.toDegrees(Math.atan((y-curY)/(x - curX)));
        if ( newAngle < 0){
            newAngle += 360;
        }

        setHeading(newAngle, executeOnTurtle);

        return newAngle - curAngle;
    }

}

package slogo.commands.turtleCommands;

import javafx.util.Pair;
import slogo.Turtle;
import slogo.commands.Command;
import slogo.commands.TurtleSpecificCommand;

import java.util.List;

public abstract class TurtleCommand extends Command implements TurtleSpecificCommand {

    public TurtleCommand(String text, List<String> args, Turtle toldTurtle) {
        super(text, args, toldTurtle);
    }

    protected void move(double distance, Turtle currentTurtle) {
        double xLoc = currentTurtle.getX();
        double yLoc = currentTurtle.getY();
        double angle = currentTurtle.getAngle();

        xLoc = xLoc + distance * Math.cos(angle);
        yLoc = yLoc + distance * Math.sin(angle);


        moveToLoc(xLoc, yLoc, currentTurtle);
    }

    protected void moveToLoc(double newX, double newY, Turtle currentTurtle) {
        currentTurtle.setLocation(newX, newY);
    }

    protected void setHeading(double newAngle, Turtle currentTurtle) {
        currentTurtle.setAngle(newAngle);
    }

    protected Pair<Double, Double> getPosition(Turtle currentTurtle) {
        double xPos = currentTurtle.getX();
        double yPos = currentTurtle.getY();
        return new Pair(xPos, yPos);
    }

    protected void togglePenState() {
        // TODO
    }

    protected void togglePenVisibility() {
        // TODO
    }

    protected void checkNegativeArgumentException(double distance) {
        // TODO
    }

}

package slogo.commands;

import javafx.util.Pair;
import slogo.structs.CommandStruct;
import slogo.view.Turtle;

import java.util.List;

public abstract class TurtleCommand extends Command implements TurtleSpecificCommand {

    public TurtleCommand(CommandStruct commandStruct, String text, List<String> args, Turtle toldTurtle) {
        super(commandStruct, text, args, toldTurtle);
    }

    protected void move(double distance, Turtle currentTurtle) {
        double xLoc = currentTurtle.getX();
        double yLoc = currentTurtle.getY();
        double angle = Math.toRadians(currentTurtle.getAngle());

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

    protected void turn(double deltaAngle, Turtle currentTurtle){
        double currentAngle = currentTurtle.getAngle();
        setHeading(currentAngle + deltaAngle, currentTurtle);
    }

    protected Pair<Double, Double> getPosition(Turtle currentTurtle) {
        double xPos = currentTurtle.getX();
        double yPos = currentTurtle.getY();
        return new Pair(xPos, yPos);
    }

    protected void checkNegativeArgumentException(double distance) {
        // TODO
    }

}

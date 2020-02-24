package slogo;

import java.util.List;

public abstract class Command implements CommandInterface {
    private static String myText;
    private List<Turtle> myTurtles;

    public Command(String text, List<Turtle> toldTurtles){
        myText = text;
        myTurtles = toldTurtles;
    }

    public double executeAllToldTurtles() {
        double retValue = -1.0;

        if (this instanceof TurtleCommand) {
            for (Turtle currentTurtle : myTurtles) {
                retValue = execute();
            }
        } else {
            retValue = execute();
        }
        return retValue;
    }

    protected abstract double execute();
}

package slogo.commands;

import slogo.Turtle;

import java.util.ArrayList;
import java.util.List;

public abstract class Command implements CommandInterface {
    private static String myText;
    private List<String> myArgs;
    private List<Turtle> myTurtles;

    public Command(String text, List<String> args, List<Turtle> toldTurtles){
        myText = text;
        myArgs = args;
        myTurtles = toldTurtles;
    }

    public double executeAllToldTurtles() {
        double retValue = -1.0;

        if (this instanceof TurtleSpecificCommand) {
            for (Turtle currentTurtle : myTurtles) {
                retValue = execute();
            }
        } else {
            retValue = execute();
        }
        return retValue;
    }

    protected abstract double execute();

    protected List<Double> getArgsDouble() {
        List<Double> doubleList = new ArrayList<>();
        for (String stringArgument : myArgs) {
            doubleList.add(Double.parseDouble(stringArgument));
        }
        return doubleList;
    }
}

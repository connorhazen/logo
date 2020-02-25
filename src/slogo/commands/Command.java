package slogo.commands;

import slogo.Turtle;

import java.util.ArrayList;
import java.util.List;

public abstract class Command implements CommandInterface {
    private static String myText;
    private List<String> myArgs;
    //private List<Turtle> myTurtles;
    private Turtle myTurtle;

    public Command(String text, List<String> args, Turtle toldTurtle){
        myText = text;
        myArgs = args;
        myTurtle = toldTurtle;
        //myTurtles = toldTurtles;
    }

//    public double executeAllToldTurtles() {
//        double retValue = -1.0;
//
//        if (this instanceof TurtleSpecificCommand) {
//            for (Turtle currentTurtle : myTurtles) {
//                retValue = execute(currentTurtle);
//            }
//        } else {
//            retValue = execute(null);
//        }
//        return retValue;
//    }

    public double executeCommand(){
        double retValue = -1.0;

        if (this instanceof TurtleSpecificCommand) {
            retValue = execute(myTurtle);
        } else {
            retValue = execute(null);
        }
        return retValue;
    }

    protected abstract double execute(Turtle executeOnTurtle);

    protected List<Double> getArgsDouble() {
        List<Double> doubleList = new ArrayList<>();
        for (String stringArgument : myArgs) {
            doubleList.add(Double.parseDouble(stringArgument));
        }
        return doubleList;
    }
}

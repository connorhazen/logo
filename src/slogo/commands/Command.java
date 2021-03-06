package slogo.commands;

import slogo.exceptions.InvalidParameterException;
import slogo.exceptions.UnknownCommandException;
import slogo.structs.CommandStruct;
import slogo.view.Turtle;

import java.util.ArrayList;
import java.util.List;

public abstract class Command implements CommandInterface {
    private static String myText;
    private List<String> myArgs;
    private CommandStruct myCommandStruct;

    private int myNumArgs;

    public Command(CommandStruct commandStruct, String text, List<String> args, Turtle toldTurtle){
        myText = text;
        myArgs = args;
        myCommandStruct = commandStruct;
        myCommandStruct.addTurtle(toldTurtle);
        //TODO: pass around updated commandStruct object
    }


    public double executeCommand() throws UnknownCommandException, InvalidParameterException {
        double retValue = -1.0;

        if (this instanceof TurtleSpecificCommand || this instanceof Repeat) {
            for(Turtle toldTurtle : getCommandStruct().getTurtleSet()){
                getCommandStruct().setActiveTurtle(toldTurtle);
                retValue = execute(toldTurtle);
            }

        } else {
            retValue = execute(null);
        }
        return retValue;
    }

    protected abstract double execute(Turtle executeOnTurtle) throws UnknownCommandException, InvalidParameterException;

    protected List<Double> getArgsDouble() {
        List<Double> doubleList = new ArrayList<>();
        for (String stringArgument : myArgs) {
            doubleList.add(Double.parseDouble(stringArgument));
        }
        return doubleList;
    }

    protected String getMyText(){
        return myText;
    }

    protected String getArgString(int index) {
        return myArgs.get(index);
    }

    protected void setMyNumArgs(int numArgs){
        myNumArgs = numArgs;
    }

    public int getNumArgs(){
        return myNumArgs;
    }

    protected CommandStruct getCommandStruct() {
        return myCommandStruct;
    }

}

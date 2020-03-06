package slogo.commands;

import slogo.exceptions.InvalidParameterException;
import slogo.exceptions.UnknownCommandException;
import slogo.view.Turtle;
import slogo.structs.CommandStruct;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


//TODO: implement execute
public class For extends Misc{
    private static final int startIndex = 0;
    private static final int endIndex = 1;
    private static final int increment = 2;

    public For(CommandStruct commandStruct, String text, List<String> args, Turtle toldTurtle) {
        super(commandStruct, text, args, toldTurtle);
        setMyNumArgs(0);
    }

    @Override
    protected double execute(Turtle executeOnTurtle) throws UnknownCommandException, InvalidParameterException {
        double ret = 0;

        List<Double> loopConstants = getLoopConstants();
        double start = loopConstants.get(0);
        double end = loopConstants.get(0);
        double increment = loopConstants.get(0);

        for(double i = start; i < end; i += increment){
            getCommandStruct().getModel().runCommand(getListString1(), executeOnTurtle);

            if(i >= end - increment ){
                ret = retVal(getListString1());
            }
        }
        return ret;
    }

    private List<Double> getLoopConstants(){
        List<Double> returnList = new ArrayList<>();

        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(getListString1());
        while(m.find()) {
            returnList.add(Double.parseDouble(m.group()));
        }
        return returnList;
    }



}

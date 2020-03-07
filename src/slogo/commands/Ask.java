package slogo.commands;

import slogo.Model;
import slogo.Parser;
import slogo.exceptions.InvalidParameterException;
import slogo.exceptions.UnknownCommandException;
import slogo.structs.CommandStruct;
import slogo.view.Turtle;

import java.util.HashSet;
import java.util.List;

public class Ask extends Misc{
    public Ask(CommandStruct commandStruct, String text, List<String> args, Turtle toldTurtle) {
        super(commandStruct, text, args, toldTurtle);
        setMyNumArgs(0);
    }

    @Override
    protected double execute(Turtle executeOnTurtle) throws UnknownCommandException, InvalidParameterException {
        List<Double> idList = getLoopConstants();
        HashSet<Turtle> prevSet = (HashSet) getCommandStruct().getTurtleSet();
        HashSet<Turtle> executeOnTurtlesSet = new HashSet<>();

        CommandStruct s = getCommandStruct();
        Model m = s.getModel();
        Parser p = new Parser(m.getLanguage(), s);

        for(double id : idList){
            int intID = (int) Math.round(id);
            Turtle currentTurtle = getCommandStruct().getTurtle(intID);
            executeOnTurtlesSet.add(currentTurtle);
        }

        getCommandStruct().setTurtleSet(executeOnTurtlesSet);

        getCommandStruct().getModel().runCommand(getListString2(), executeOnTurtle);
        List<String> parsed = p.parseCommand(getListString2());
        double ret = p.getCommandRetValue(parsed.get(parsed.size()-1));

        getCommandStruct().setTurtleSet(prevSet);

        return ret;
    }
}

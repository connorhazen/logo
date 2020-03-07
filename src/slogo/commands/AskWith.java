package slogo.commands;

import slogo.Model;
import slogo.Parser;
import slogo.exceptions.InvalidParameterException;
import slogo.exceptions.UnknownCommandException;
import slogo.structs.CommandStruct;
import slogo.view.Turtle;

import java.util.HashSet;
import java.util.List;

public class AskWith extends Misc {
    public AskWith(CommandStruct commandStruct, String text, List<String> args, Turtle toldTurtle) {
        super(commandStruct, text, args, toldTurtle);
        setMyNumArgs(0);
    }

    @Override
    protected double execute(Turtle executeOnTurtle) throws UnknownCommandException, InvalidParameterException {
        HashSet<Turtle> executeOnTurtleSet = new HashSet<>();
        HashSet<Turtle> prevSet = (HashSet) getCommandStruct().getTurtleSet();

        for(Turtle turtle : getCommandStruct().getFullTurtleSet()){
            HashSet<Turtle> temp = new HashSet<>();
            temp.add(turtle);
            getCommandStruct().setTurtleSet(temp);
            double conditional = retVal(getListString1());
            if(conditional != 0){
                executeOnTurtleSet.add(turtle);
            }
        }

        CommandStruct s = getCommandStruct();
        Model m = s.getModel();
        Parser p = new Parser(m.getLanguage(), s);

        getCommandStruct().setTurtleSet(executeOnTurtleSet);

        getCommandStruct().getModel().runCommand(getListString2(), executeOnTurtle);
        List<String> parsed = p.parseCommand(getListString2());
        double ret = p.getCommandRetValue(parsed.get(parsed.size()-1));

        getCommandStruct().setTurtleSet(prevSet);

        return ret;
    }
}

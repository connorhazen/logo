package slogo.commands;

import slogo.Model;
import slogo.Parser;
import slogo.view.Turtle;
import slogo.exceptions.InvalidParameterException;
import slogo.exceptions.UnknownCommandException;
import slogo.structs.CommandStruct;

import java.util.List;

public class Repeat extends Misc {


    public Repeat(CommandStruct commandStruct, String text, List<String> args, Turtle toldTurtle) {
        super(commandStruct, text, args, toldTurtle);
        setMyNumArgs(2);
    }

    @Override
    protected double execute(Turtle executeOnTurtle) throws UnknownCommandException, InvalidParameterException {
        double ret = 0;
        double runNumTimes = Double.parseDouble(getArgString(0));
        String cleanArg = getArgString(1).replace("[","").replace("]","").trim();
        CommandStruct s = getCommandStruct();
        Model m = s.getModel();
        Parser p = new Parser("English", s);

        for(int i = 0; i < runNumTimes; i++){
            getCommandStruct().getModel().runCommand(cleanArg, executeOnTurtle);
            if(i == runNumTimes - 1){
                List<String> parsed = p.parseCommand(cleanArg);
                ret = p.getCommandRetValue(parsed.get(parsed.size()-1));
            }

        }

        return ret;
    }


}

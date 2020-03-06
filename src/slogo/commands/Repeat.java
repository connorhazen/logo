package slogo.commands;

import slogo.view.Turtle;
import slogo.exceptions.InvalidParameterException;
import slogo.exceptions.UnknownCommandException;
import slogo.structs.CommandStruct;

import java.util.List;

public class Repeat extends Misc {


    public Repeat(CommandStruct commandStruct, String text, List<String> args, Turtle toldTurtle) {
        super(commandStruct, text, args, toldTurtle);
        setMyNumArgs(1);
    }

    @Override
    protected double execute(Turtle executeOnTurtle) throws UnknownCommandException, InvalidParameterException {
        double ret = 0;
        double runNumTimes = getArgsDouble().get(0);
        for(int i = 0; i < runNumTimes; i++){
            getCommandStruct().getModel().runCommand(getListString1(), executeOnTurtle);

            if(i == runNumTimes - 1){
                ret = retVal(getListString1());
                // ret = getCommandStruct().getModel().getParser().getCommandRetValue();
                // TODO: access argument stack in parser to get the actual command being executed or write method:
                // Could this just call the parser on the passed in String?
            }

        }

        return ret;
    }


}

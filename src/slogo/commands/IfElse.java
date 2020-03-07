package slogo.commands;

import slogo.Model;
import slogo.Parser;
import slogo.view.Turtle;
import slogo.exceptions.InvalidParameterException;
import slogo.exceptions.UnknownCommandException;
import slogo.structs.CommandStruct;

import java.util.List;

public class IfElse extends Misc {
    public IfElse(CommandStruct commandStruct, String text, List<String> args, Turtle toldTurtle) {
        super(commandStruct, text, args, toldTurtle);
        setMyNumArgs(1);
    }

    @Override
    protected double execute(Turtle executeOnTurtle) throws UnknownCommandException, InvalidParameterException {
        double ret = 0;
        CommandStruct s = getCommandStruct();
        Model m = s.getModel();
        Parser p = new Parser(m.getLanguage(), s);

        if(getArgsDouble().get(0) != 0){ //TODO: refactor
            getCommandStruct().getModel().runCommand(getListString1(), executeOnTurtle);
            if(getListString1().length() > 1){
                List<String> parsed = p.parseCommand(getListString1());
                ret = p.getCommandRetValue(parsed.get(parsed.size()-1));
            }
        }else{
            getCommandStruct().getModel().runCommand(getListString2(), executeOnTurtle);
            if(getListString2().length() > 1){
                List<String> parsed = p.parseCommand(getListString2());
                ret = p.getCommandRetValue(parsed.get(parsed.size()-1));
            }
        }

        return ret;
    }
}

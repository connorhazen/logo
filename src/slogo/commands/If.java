package slogo.commands;

import slogo.Model;
import slogo.Parser;
import slogo.exceptions.InvalidParameterException;
import slogo.exceptions.UnknownCommandException;
import slogo.structs.CommandStruct;
import slogo.view.Turtle;

import java.util.List;

public class If extends Misc{
    public If(CommandStruct commandStruct, String text, List<String> args, Turtle toldTurtle) {
        super(commandStruct, text, args, toldTurtle);
        setMyNumArgs(1);
    }

    @Override
    protected double execute(Turtle executeOnTurtle) throws UnknownCommandException, InvalidParameterException {
        double ret = 0;

        String cleanArg = getArgString(1).replaceFirst("\\[","").trim();
        cleanArg = replaceLast(cleanArg, "]", "");
        CommandStruct s = getCommandStruct();
        Model m = s.getModel();
        Parser p = new Parser(m.getLanguage(), s);

        if(getArgsDouble().get(0) != 0){
            getCommandStruct().getModel().runCommand(getListString1(), executeOnTurtle);
            if(getListString1().length() > 1){
                List<String> parsed = p.parseCommand(cleanArg);
                ret = p.getCommandRetValue(parsed.get(parsed.size()-1));
            }
        }

        return ret;
    }
}

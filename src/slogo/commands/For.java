package slogo.commands;

import slogo.Model;
import slogo.Parser;
import slogo.exceptions.InvalidParameterException;
import slogo.exceptions.UnknownCommandException;
import slogo.structs.VariableStruct;
import slogo.view.Turtle;
import slogo.structs.CommandStruct;

import java.util.List;


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
        double end = loopConstants.get(1);
        double increment = loopConstants.get(2);
        String varName = parseStringIntoVar(getListString1()).get(0);

        CommandStruct s = getCommandStruct();
        Model m = s.getModel();
        Parser p = new Parser(m.getLanguage(), s);

        for(double i = start; i < end; i += increment){
            VariableStruct itterator = new VariableStruct(varName, i);
            getCommandStruct().addVariable(itterator);
            getCommandStruct().getModel().runCommand(getListString2(), executeOnTurtle);

            if(i >= end - increment ){
                List<String> parsed = p.parseCommand(getListString2());
                ret = p.getCommandRetValue(parsed.get(parsed.size()-1));
            }
        }
        return ret;
    }


}

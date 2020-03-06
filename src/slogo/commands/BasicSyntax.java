package slogo.commands;

import slogo.Parser;
import slogo.exceptions.InvalidParameterException;
import slogo.structs.CommandStruct;
import slogo.view.Turtle;

import java.util.ArrayList;
import java.util.List;

public abstract class BasicSyntax extends Command {
    public BasicSyntax(CommandStruct commandStruct, String text, List<String> args, Turtle toldTurtle) {
        super(commandStruct, text, args, toldTurtle);
    }

    protected String firstWord(String text){
        int i = 0;
        int varStringBeginIndex = 0;
        boolean unclosedVar = false;
        while(i < text.length()){
            char curChar = text.charAt(i);
            if(Character.isLetter(curChar) ||curChar== '_'){
                varStringBeginIndex = i;
                unclosedVar = true;
            }
            if(unclosedVar && ( (curChar == ' ' || curChar == '\t' || curChar == '\r' || curChar == '\n' ))){
                return text.substring(varStringBeginIndex, i);
            }
            if(unclosedVar && i == text.length() - 1){
                return text.substring(varStringBeginIndex, i);
            }
            i++;
        }
        return null; // unreachable statement
    }

    protected double retVal(String basicCmd) throws InvalidParameterException {
        Parser p = new Parser(getCommandStruct().getModel().getLanguage(), getCommandStruct());
        return p.getCommandRetValue(basicCmd);
    }


}

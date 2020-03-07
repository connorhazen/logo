package slogo.commands;


import slogo.Parser;
import slogo.exceptions.UnknownCommandException;
import slogo.view.Turtle;
import slogo.exceptions.InvalidParameterException;

import slogo.structs.CommandStruct;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//TODO: implement looping mechanism
public abstract class Misc extends Command {
    private static final String listBegin = "[";
    private static final String listEnd = "]";
    private static final String varBegin = ":";

    private String myExpression;
    private String myListString1;
    private String myListString2;
    private List<String> myBasicCommands;

    public Misc(CommandStruct commandStruct, String text, List<String> args, Turtle toldTurtle) {
        super(commandStruct, text, args, toldTurtle);
        parseInputText();
    }

    protected void executeCommand(String expr, Turtle executeOnTurtle) throws UnknownCommandException, InvalidParameterException {
         getCommandStruct().getModel().runCommand(expr, executeOnTurtle);
    }

    protected String getExpression(){ return myExpression;}

    protected String getListString1(){
        return myListString1;
    }

    protected String getListString2(){
        return myListString2;
    }

    private void parseInputText(){
        String textInput = getMyText();
        int listBeginIndex = textInput.indexOf(listBegin);
        int listEndIndex = textInput.indexOf(listEnd);

        if(listBeginIndex >= 0){
        myExpression = textInput.substring(0,listBeginIndex);

            if(listEndIndex >= 0) {

                myListString1 = stringIterator(textInput, listBeginIndex, listEndIndex);

                textInput = textInput.substring(listBeginIndex + 1);
                listBeginIndex = textInput.indexOf(listBegin);
                listEndIndex = textInput.indexOf(listEnd);

                if (listBeginIndex >= 0 && listEndIndex >= 0) {
                    myListString2 = stringIterator(textInput, listBeginIndex, listEndIndex);
                }
            }
        }
    }
    private String stringIterator(String text, int listBeginIndex, int listEndIndex){
        int unclosedBrackets = 1;

        for(int i = listBeginIndex + 1; i < text.length(); i++){
            if(text.charAt(i) == listBegin.charAt(0)){
                unclosedBrackets++;
            }
            if(text.charAt(i) == listEnd.charAt(0)){
                unclosedBrackets--;
            }
            if(unclosedBrackets == 0){
                listEndIndex = i;
                break;
            }
        }
        if(unclosedBrackets < 0){
            //TODO: throw error
        }
        return text.substring(listBeginIndex + 1, listEndIndex);
    }


    protected List<String> parseStringIntoVar(String string){
        List<String> retList = new ArrayList<>();

        int i = 0;
        int varStringBeginIndex = 0;
        boolean unclosedVar = false;
        while(i < string.length()){
            char curChar = string.charAt(i);
            if(curChar == varBegin.charAt(0)){
                varStringBeginIndex = i;
                unclosedVar = true;
            }
            if(unclosedVar && ( (curChar == ' ' || curChar == '\t' || curChar == '\r' || curChar == '\n' ))){
                String insertString = string.substring(varStringBeginIndex+1, i);
                unclosedVar = false;
                retList.add(insertString);
            }
            if(unclosedVar && i == string.length() - 1){
                String insertString = string.substring(varStringBeginIndex+1, i); //TODo: add exception
                retList.add(insertString);
            }
            i++;
        }
        return retList;
    }

    protected double retVal(String basicCmd) throws InvalidParameterException {
        Parser p = new Parser(getCommandStruct().getModel().getLanguage(), getCommandStruct());
        return p.getCommandRetValue(basicCmd);

    }

    protected static String replaceLast(String text, String source, String target) {
        StringBuilder b = new StringBuilder(text);
        b.replace(text.lastIndexOf(source), text.lastIndexOf(source) + source.length(), target);
        return b.toString();
    }

    protected List<Double> getLoopConstants(){ //TODO: rename
        List<Double> returnList = new ArrayList<>();

        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(getListString1());
        while(m.find()) {
            returnList.add(Double.parseDouble(m.group()));
        }
        return returnList;
    }
}

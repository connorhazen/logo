package slogo.commands;


import slogo.Parser;
import slogo.view.Turtle;
import slogo.commands.Command;
import slogo.exceptions.InvalidParameterException;

import slogo.structs.CommandStruct;
import slogo.structs.VariableStruct;

import java.util.Collections;
import java.util.List;
import java.util.Stack;

//TODO: implement looping mechanism
public abstract class Misc extends Command {
    private static final String listBegin = "[";
    private static final String listEnd = "]";
    private String myListString1;
    private String myListString2;
    private List<String> myBasicCommands;

    public Misc(CommandStruct commandStruct, String text, List<String> args, Turtle toldTurtle) {
        super(commandStruct, text, args, toldTurtle);
    }

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

        if(listBeginIndex >= 0 && listEndIndex >= 0){

            myListString1 = stringIterator(textInput, listBeginIndex, listEndIndex);

            textInput = textInput.substring(listBeginIndex + 1);
            listBeginIndex = textInput.indexOf(listBegin);
            listEndIndex = textInput.indexOf(listEnd);

            if(listBeginIndex >= 0 && listEndIndex >= 0){
                myListString2 = stringIterator(textInput, listBeginIndex, listEndIndex);
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

//    protected void populateBasicCommands(){
//        myBasicCommands = getCommandStruct().getModel().runCommand()
//    }

    private String lastCommandInString(String input){ // find last "[" and then find the begining of a char substring
        int commandStartIndex = 0;
        int listBeginIndex = input.indexOf(listBegin);
        if(listBeginIndex < 0){
            listBeginIndex = 0;
        }
        for(int i = listBeginIndex; i < input.length(); i++){
            if(input.charAt(i) == listBegin.charAt(0)){
                listBeginIndex = i;
            }
        }
        // now listBeginIndex points to the last occurance of "[" in the string
        // itterate backwards through the string now to find the beginning of the char substring that indicates a command
        for(int i = listBeginIndex; i >= 0; i--){
            if(commandStartIndex != 0){
                if(input.charAt(i) == ' '){
                    break;
                }
            }
            if(input.charAt(i) == listBegin.charAt(0)){
                commandStartIndex = i;
            }
        }
        return input.substring(commandStartIndex);
    }

    protected double lastRetVal(String basicCmd) throws InvalidParameterException {
        Parser p = new Parser(getCommandStruct().getModel().getLanguage(), getCommandStruct());
        return p.getCommandRetValue(basicCmd);
    }



}

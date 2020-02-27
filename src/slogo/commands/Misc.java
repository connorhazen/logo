package slogo.commands;

import slogo.Turtle;
import slogo.commands.Command;
import slogo.structs.CommandStruct;
import slogo.structs.VariableStruct;

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

            myList1 = stringIterator(textInput, listBeginIndex, listEndIndex);

            textInput = textInput.substring(listBeginIndex + 1);
            listBeginIndex = textInput.indexOf(listBegin);
            listEndIndex = textInput.indexOf(listEnd);

            if(listBeginIndex >= 0 && listEndIndex >= 0){
                myList2 = stringIterator(textInput, listBeginIndex, listEndIndex);
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


}

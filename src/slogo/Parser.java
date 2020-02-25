package slogo;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Parser implements ParserInterface{
    private Map<String, String> savedCommands;
    private static final String SAVE_SYMBOL = "=";
    private static final int COMMAND_TO_SAVE_INDEX = 0;
    private static final int SAVE_SYMBOL_INDEX = 1;

    public Parser(){
        savedCommands = new HashMap<>();
    }

    public String[] parseCommand(String cmd){
        String[] parsedCommand = cmd.split(" ");
        if(parsedCommand[SAVE_SYMBOL_INDEX].equals(SAVE_SYMBOL)){
            saveCommand(parsedCommand);
        }
        return convertToBasicCommands(parsedCommand);
    }

    private String[] convertToBasicCommands(String[] originalCmd){
        Stack argumentStack = new Stack();
        Stack commandStack = new Stack();

        return null;
    }

    private void saveCommand(String[] parsedCommand) {
        String commandToSave = "";
        for(int i = 0; i < parsedCommand.length; i++){
            if(i == COMMAND_TO_SAVE_INDEX || i == SAVE_SYMBOL_INDEX) {continue;}
            else if (i == parsedCommand.length - 1) {commandToSave += parsedCommand[i];}
            else {commandToSave += parsedCommand[i] + " ";}
        }
        savedCommands.put(parsedCommand[COMMAND_TO_SAVE_INDEX], commandToSave);
    }

    /**
     * Returns an immutable map containing the saved commands which can be displayed in the view.
     */
    public Map<String, String> getSavedCommands(){
        return Collections.unmodifiableMap(savedCommands);
    }
}

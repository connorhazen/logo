package slogo;

import java.util.*;
import java.util.regex.Pattern;

public class Parser implements ParserInterface{
    private Map<String, String> savedCommands;
    private Pattern integerPattern;
    private Stack argumentStack;
    private Stack commandStack;
    private static final String SAVE_SYMBOL = "=";
    private static final int COMMAND_TO_SAVE_INDEX = 0;
    private static final int SAVE_SYMBOL_INDEX = 1;
    private static final String INTEGER_PATTERN_REGEX = "-?[0-9]*";

    // TODO: Need to obtain correct resource file from user selection
    private ResourceBundle languageResource = ResourceBundle.getBundle("resources.languages/English");

    public Parser(){
        savedCommands = new HashMap<>();
        integerPattern = Pattern.compile(INTEGER_PATTERN_REGEX);
    }

    public List<String> parseCommand(String cmd){
        String cleanCommand = cmd.replace(System.getProperty("line.separator"), "");
        String[] parsedCommand = cleanCommand.split(" ");
        if(parsedCommand.length > SAVE_SYMBOL_INDEX && parsedCommand[SAVE_SYMBOL_INDEX].equals(SAVE_SYMBOL)){
            saveCommand(parsedCommand);
        }
        return convertToBasicCommands(parsedCommand);
    }

    private List<String> convertToBasicCommands(String[] originalCmd){
        argumentStack = new Stack();
        commandStack = new Stack();
        List<String> basicCommandList = new ArrayList<>();

        for(String s : originalCmd) {
            if(isInteger(s)) {
                argumentStack.push(s);
            }
            else {
                commandStack.push(s);
            }
            basicCommandList = buildTree(basicCommandList);
        }
        return basicCommandList;
    }

    private List<String> buildTree(List<String> commandList){
        while(!commandStack.empty()){
            String command = languageResource.getString((String) commandStack.peek());
            // If command in list of commands, check if argumentStack.size is at least # of arguments for command.
            // If so, pop from command stack and pop number of arguments, get return value and add to argumentStack.
            // Append basic command to commandList.
            // Break if not.
        }
        return commandList;
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

    private boolean isInteger(String s){
        if(s == null) {return false;}
        return integerPattern.matcher(s).matches();
    }

    /**
     * Returns an immutable map containing the saved commands which can be displayed in the view.
     */
    public Map<String, String> getSavedCommands(){
        return Collections.unmodifiableMap(savedCommands);
    }
}

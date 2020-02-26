package slogo;

import slogo.exceptions.UnknownCommandException;
import slogo.structs.CommandStruct;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Pattern;

import static java.lang.Class.forName;

public class Parser implements ParserInterface{
    private Map<String, String> savedCommands;
    private Pattern integerPattern;
    private Stack argumentStack;
    private Stack commandStack;
    private Map<String, String> commandMap;
    private ResourceBundle languageResource;
    private static final String SAVE_SYMBOL = "=";
    private static final int COMMAND_INDEX = 0;
    private static final int SAVE_SYMBOL_INDEX = 1;
    private static final String DOUBLE_PATTERN_REGEX = "-?[0-9]*(\\.[0-9]*)?";
    private static final Turtle DUMMY_TURTLE = new Turtle(0,0,0,0,0);
    private static final Class<?> NOPARAMS[] = {};
    private static final Class<?> COMMAND_CLASS_PARAMS[] = new Class<?>[] {CommandStruct.class, String.class, List.class, Turtle.class};
    private static final Object COMMAND_PARAMS[] = new Object[] {new CommandStruct(""), "", new ArrayList<>(), DUMMY_TURTLE};
    private static final Class<?> EXECUTE_CLASS_PARAMS[] = new Class<?>[] {Turtle.class};
    private static final Object EXECUTE_PARAMS[] = new Object[] {DUMMY_TURTLE};

    // TODO: Need to obtain correct resource file from user selection
    private String languageFile = "resources.languages/English";

    public Parser() {
        savedCommands = new HashMap<>();
        commandMap = new HashMap<>();
        integerPattern = Pattern.compile(DOUBLE_PATTERN_REGEX);
        languageResource = ResourceBundle.getBundle(languageFile);
        fillCommandMap();
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

        System.out.println(commandMap); // REGEX for commands
        for(String s : originalCmd) {

            if(isInteger(s)) {
                argumentStack.push(s);
            }
            else if (commandMap.containsKey(s)){
                commandStack.push(s);
            }
            else {
                throw new UnknownCommandException("Command not recognized: " + s);
                // TODO: Error? OR if commandMap does not contain user-defined commands/variable, check if it's one of those
            }
            basicCommandList = buildTree(basicCommandList);
        }
        return basicCommandList;
    }

    private List<String> buildTree(List<String> commandList){
        while(!commandStack.empty()){
            String basicCommand = "";
            String command = (String) commandStack.peek();
            if (commandMap.containsKey(command)) {
                String commandClassName = commandMap.get(command);
                int commandNumArgs = getCommandNumArgs(commandClassName);
                if(argumentStack.size() >= commandNumArgs){
                    basicCommand += commandStack.pop();
                    if(commandNumArgs > 0) {
                        List<String> arguments = new ArrayList<>();
                        for (int i = 0; i < commandNumArgs; i++) {
                            String arg = argumentStack.pop().toString();
                            arguments.add(arg);
                        }
                        Collections.reverse(arguments);
                        for (String arg : arguments) {
                            basicCommand += " " + arg;
                        }
                        argumentStack.push(getCommandRetValue(basicCommand));
                    }
                    commandList.add(basicCommand);
                }
                else{ break; }
            }
            else { throw new UnknownCommandException("Command not recognized: " + command); } // TODO: Put message in properties files
        }
        if(commandStack.empty()) { argumentStack.clear(); }
        return commandList;
    }

    private int getCommandNumArgs(String cmd) {
        try {
            Class cls = forName("slogo.commands." + cmd);
            Constructor cons = cls.getDeclaredConstructor(COMMAND_CLASS_PARAMS);
            Object obj = cons.newInstance(COMMAND_PARAMS);
            Method method = cls.getMethod("getNumArgs", NOPARAMS);
            return (int) method.invoke(obj);
        } catch (Exception e) {
            e.printStackTrace(); // TODO: Throw error
            return Integer.MAX_VALUE;
        }
    }

    private double getCommandRetValue(String cmd){
        String[] parsedCommand = cmd.split(" ");
        String command = commandMap.get(parsedCommand[COMMAND_INDEX]);
        List<String> args = new ArrayList<>();
        for (int i = 1; i < parsedCommand.length; i++) {
            args.add(parsedCommand[i]);
        }
        try {
            Class cls = forName("slogo.commands." + command);
            Constructor cons = cls.getDeclaredConstructor(COMMAND_CLASS_PARAMS);
            Object params[] = new Object[] {new CommandStruct(""), "", args, DUMMY_TURTLE};
            Object obj = cons.newInstance(params);
            Method method = cls.getDeclaredMethod("execute", EXECUTE_CLASS_PARAMS);
            method.setAccessible(true);
            return (double) method.invoke(obj, EXECUTE_PARAMS);
        } catch (Exception e) {
            e.printStackTrace(); // TODO: Throw error
        }
        return 0;
    }

    private void saveCommand(String[] parsedCommand) {
        String commandToSave = "";
        for(int i = 0; i < parsedCommand.length; i++){
            if(i == COMMAND_INDEX || i == SAVE_SYMBOL_INDEX) {continue;}
            else if (i == parsedCommand.length - 1) {commandToSave += parsedCommand[i];}
            else {commandToSave += parsedCommand[i] + " ";}
        }
        savedCommands.put(parsedCommand[COMMAND_INDEX], commandToSave);
    }

    private boolean isInteger(String s){
        if(s == null) {return false;}
        return integerPattern.matcher(s).matches();
    }

    private void fillCommandMap(){
        Enumeration<String> commands = languageResource.getKeys();
        while(commands.hasMoreElements()){
            String commandName = commands.nextElement();
            String cmds = languageResource.getString(commandName);
            String[] recognizedCmds = cmds.split("\\|");
            for (String recognizedCmd : recognizedCmds) {
                commandMap.put(recognizedCmd, commandName);
            }
        }
    }

    /**
     * Returns an immutable map containing the saved commands which can be displayed in the view.
     */
    public Map<String, String> getSavedCommands(){
        return Collections.unmodifiableMap(savedCommands);
    }
}

package slogo;

import slogo.commands.Command;
import slogo.commands.Variable;
import slogo.exceptions.InvalidParameterException;
import slogo.exceptions.UnknownCommandException;
import slogo.structs.CommandStruct;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Pattern;

import slogo.structs.VariableStruct;
import slogo.view.Turtle;

import static java.lang.Class.forName;

public class Parser {
    private Map<String, String> savedCommands;
    private Stack argumentStack;
    private Stack commandStack;
    private Map<String, String> commandMap;
    private ResourceBundle languageResource;
    private String currentLanguage;
    private CommandStruct commandStruct;
    private CommandStruct dummyCommandStruct;

    private static final Pattern CONSTANT_PATTERN = Pattern.compile("-?[0-9]+\\.?[0-9]*");
    private static final Pattern COMMENT_PATTERN = Pattern.compile("^#.*");
    private static final String SAVE_SYMBOL = "=";
    public static final int COMMAND_INDEX = 0;
    private static final int SAVE_SYMBOL_INDEX = 1;
    private static final Turtle DUMMY_TURTLE = new Turtle(0,0,0,0);
    public static final Class<?> NOPARAMS[] = {};
    public static final Class<?> COMMAND_CLASS_PARAMS[] = new Class<?>[] {CommandStruct.class, String.class, List.class, Turtle.class};
    private static final Object COMMAND_PARAMS[] = new Object[] {new CommandStruct(new Model()), "[ ]", new ArrayList<>(), DUMMY_TURTLE};
    private static final Class<?> EXECUTE_CLASS_PARAMS[] = new Class<?>[] {Turtle.class};
    private static final Object EXECUTE_PARAMS[] = new Object[] {DUMMY_TURTLE};
    private static final String LIST_BEGIN_SYMBOL = "[";
    private static final String LIST_END_SYMBOL = "]";
    private static final String PACKAGE = Command.class.getPackageName();
    private static final String LANGUAGE_PACKAGE = "resources.languages/";
    private static final ResourceBundle ERROR_MESSAGES = ResourceBundle.getBundle("slogo/exceptions/exception_messages");

    /**
     * This is the constructor for the parser class
     * @param language String of language to be used in parsing
     * @param cs CommandStruct passed in from the model which contains user-defined commands and variables
     */
    public Parser(String language, CommandStruct cs) {
        savedCommands = new HashMap<>();
        commandMap = new HashMap<>();
        currentLanguage = language;
        languageResource = ResourceBundle.getBundle(LANGUAGE_PACKAGE + language);
        getCommandMap();
        argumentStack = new Stack();
        commandStack = new Stack();
        commandStruct = cs;
        dummyCommandStruct = new CommandStruct(new Model(currentLanguage));
    }

    /**
     * Takes a command of any length and parses it into commands that can be recognized and run by the model
     * @param cmd String of command to be parsed
     * @return List of strings containing commands for model after parsing
     */
    public List<String> parseCommand(String cmd) throws UnknownCommandException, InvalidParameterException {
        String cleanCommand = removeComments(cmd);
        cleanCommand = cleanCommand.trim().replaceAll(" +", " ");
        String[] parsedCommand = cleanCommand.split(" ");
        if(parsedCommand.length > SAVE_SYMBOL_INDEX && parsedCommand[SAVE_SYMBOL_INDEX].equals(SAVE_SYMBOL)){
            saveCommand(parsedCommand);
        }
        return convertToBasicCommands(parsedCommand);
    }

    private String removeComments(String cmd){
        String noComments = "";
        String[] cmdByLine = cmd.split(System.getProperty("line.separator"));
        for(String line : cmdByLine){
            if(line.equals("")) { continue; }
            if(COMMENT_PATTERN.matcher(String.valueOf(line.charAt(0))).matches()){ continue; }
            else { noComments += (line + " "); }
        }
        return noComments;
    }

    private List<String> convertToBasicCommands(String[] originalCmd) throws UnknownCommandException, InvalidParameterException {
        clearStacks();
        List<String> basicCommandList = new ArrayList<>(); boolean isSlogoList = false; String listCommand = ""; int beginCount = 0; int endCount = 0; boolean userDef = false;
        for(String s : originalCmd) {
            if(s.equals(LIST_BEGIN_SYMBOL)){ isSlogoList = true;}
            if(isSlogoList){
                if(s.equals(LIST_BEGIN_SYMBOL)) {beginCount += 1; listCommand += s + " ";}
                else if(s.equals(LIST_END_SYMBOL)) {
                    endCount += 1;
                    if(beginCount == endCount) { listCommand += s; argumentStack.push(listCommand); isSlogoList = false; listCommand = ""; beginCount = 0; endCount = 0; }
                    else { listCommand += s + " "; }
                } else { listCommand += s + " "; }
            }
            else {
                if (isConstant(s) || userDef == true) { argumentStack.push(s); userDef = false; }
                else if (commandMap.containsKey(s)) { if(commandMap.get(s).equals("MakeVariable")) {userDef = true;} commandStack.push(s); }
                else if (commandStruct.containsVariable(s)) { argumentStack.push(commandStruct.getVariable(s).getValue()); }
                else if (Character.toString(s.charAt(0)).equals(":")) {commandStruct.addVariable(new VariableStruct(s, 0)); argumentStack.push(0); }
                else { throw new UnknownCommandException(ERROR_MESSAGES.getString("UnknownCommand") + s); }
            }
            basicCommandList = buildTree(basicCommandList);
        }
        return basicCommandList;
    }

    private void clearStacks(){
        argumentStack.clear();
        commandStack.clear();
    }

    private List<String> buildTree(List<String> commandList) throws UnknownCommandException, InvalidParameterException {
        while(!commandStack.empty()){
            String command = (String) commandStack.peek();
            if (commandMap.containsKey(command)) {
                String commandClassName = commandMap.get(command);
                int commandNumArgs = getCommandNumArgs(commandClassName);
                if(argumentStack.size() >= commandNumArgs){
                    addBasicCmdToList(commandList, commandNumArgs);
                }
                else{ break; }
            }
            else { throw new UnknownCommandException(ERROR_MESSAGES.getString("UnknownCommand") + command); }
        }
        if(commandStack.empty()) { argumentStack.clear(); }
        return commandList;
    }

    private void addBasicCmdToList(List<String> commandList, int commandNumArgs) throws InvalidParameterException {
        String basicCommand = "";
        basicCommand += commandStack.pop();
        if(commandNumArgs > 0) {
            List<String> arguments = new ArrayList<>();
            for (int i = 0; i < commandNumArgs; i++) {
                String arg = argumentStack.pop().toString();
                arguments.add(arg);
            }
            Collections.reverse(arguments);
            for (String arg : arguments) { basicCommand += " " + arg; }
            argumentStack.push(getCommandRetValue(basicCommand));
        }
        commandList.add(basicCommand);
    }

    private int getCommandNumArgs(String cmd) throws UnknownCommandException {
        try {
            Class cls = forName(PACKAGE + "." + cmd);
            Constructor cons = cls.getDeclaredConstructor(COMMAND_CLASS_PARAMS);
            Object obj = cons.newInstance(COMMAND_PARAMS);
            Method method = cls.getMethod("getNumArgs", NOPARAMS);
            return (int) method.invoke(obj);
        } catch (Exception e) {
            throw new UnknownCommandException(ERROR_MESSAGES.getString("UnknownCommand") + cmd);
        }
    }

    /**
     * Takes a command and returns the command's return value
     * @param cmd String of command to get return value of
     * @return return value of given command
     */
    public double getCommandRetValue(String cmd) throws InvalidParameterException {
        String[] parsedCommand = parseList(cmd);;
        String command = commandMap.get(parsedCommand[COMMAND_INDEX]);
        List<String> args = new ArrayList<>();
        for (int i = 1; i < parsedCommand.length; i++) {
            args.add(parsedCommand[i]);
        }
        try {
            Class cls = forName("slogo.commands." + command);
            Constructor cons = cls.getDeclaredConstructor(COMMAND_CLASS_PARAMS);
            Object params[] = new Object[] {dummyCommandStruct, "[ ]", args, DUMMY_TURTLE};
            if(command.equals("MakeVariable")) {params = new Object[] {commandStruct, "[ ]", args, DUMMY_TURTLE};}
            Object obj = cons.newInstance(params);
            Method method = cls.getDeclaredMethod("execute", EXECUTE_CLASS_PARAMS);
            method.setAccessible(true);
            return (double) method.invoke(obj, EXECUTE_PARAMS);
        } catch (Exception e) {
            throw new InvalidParameterException(e, ERROR_MESSAGES.getString("InvalidParameter") + cmd);
        }
    }

    /**
     * Recognizes lists and includes them as one argument instead of multiple commands or arguments
     * @param cmd String of command to parse into string array (including lists)
     * @return String array containing commands and arguments (including lists) as separate entries
     */
    public String[] parseList(String cmd){
        String[] parsedCommand = cmd.split(" ");
        ArrayList<String> cmdList = new ArrayList<>(); boolean isSlogoList = false; String listCommand = ""; int beginCount = 0; int endCount = 0;
        for(String s : parsedCommand) {
            if (s.equals(LIST_BEGIN_SYMBOL)) { isSlogoList = true; }
            if (isSlogoList) {
                if (s.equals(LIST_BEGIN_SYMBOL)) {
                    beginCount += 1;
                    listCommand += s + " ";
                } else if (s.equals(LIST_END_SYMBOL)) {
                    endCount += 1;
                    if (beginCount == endCount) {
                        listCommand += s; isSlogoList = false;
                        cmdList.add(listCommand);
                        listCommand = ""; beginCount = 0; endCount = 0;
                    } else { listCommand += s + " "; }
                } else { listCommand += s + " "; }
            } else { cmdList.add(s); }
        }
        String[] ret = new String[cmdList.size()];
        for(int i = 0; i < ret.length; i++) { ret[i] = cmdList.get(i); }
        return ret;
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

    private boolean isConstant(String s){
        if(s == null) {return false;}
        return CONSTANT_PATTERN.matcher(s).matches();
    }

    /**
     * Creates a map that can go from Slogo commands to command class names
     * @return map with language's recognized commands as keys and the command class name as values
     */
    public Map<String, String> getCommandMap(){
        Enumeration<String> commands = languageResource.getKeys();
        while(commands.hasMoreElements()){
            String commandName = commands.nextElement();
            String cmds = languageResource.getString(commandName);
            String[] recognizedCmds = cmds.split("\\|");
            for (String recognizedCmd : recognizedCmds) {
                recognizedCmd = recognizedCmd.replace("\\", "");
                commandMap.put(recognizedCmd, commandName);
            }
        }
        return commandMap;
    }
}

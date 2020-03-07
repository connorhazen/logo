package slogo;

import slogo.commands.Command;
import slogo.exceptions.InvalidParameterException;
import slogo.exceptions.UnknownCommandException;
import slogo.structs.CommandStruct;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.*;

import slogo.structs.UserCommandStruct;
import slogo.structs.VariableStruct;
import slogo.view.Turtle;

import static java.lang.Class.forName;

public class Model implements ModelInterface{
    private CommandStruct commandStruct;
    private String language;
    private static final String PACKAGE = Command.class.getPackageName();
    private static final ResourceBundle ERROR_MESSAGES = ResourceBundle.getBundle("slogo/exceptions/exception_messages");
    private static final String LANGUAGE_DEFAULT = "English";

    /**
     * This is the constructor for the model which uses English as the default language.
     */
    public Model(){
        this(LANGUAGE_DEFAULT);
    }

    /**
     * This is the constructor for the model which uses the given language in parsing commands
     * @param lang String of language to be used in model
     */
    public Model(String lang){
        commandStruct = new CommandStruct(this);
        language = lang;
    }

    @Override
    public CommandStruct getCommandStruct(){
        return commandStruct;
    }

    @Override
    public List<String> runCommand(String input, Turtle turtle) throws UnknownCommandException, InvalidParameterException {
        if(input.trim().equals("")) { throw new UnknownCommandException(ERROR_MESSAGES.getString("NoCommand")); }
        Parser parser = new Parser(language, commandStruct);
        List<String> parsedCommands = parser.parseCommand(input);

        Map<String, String> commandMap = parser.getCommandMap();

        for (String basicCmd : parsedCommands) {
            String[] parsedCommand = parser.parseList(basicCmd);
            String command = commandMap.get(parsedCommand[Parser.COMMAND_INDEX]);
            List<String> args = new ArrayList<>();
            for (int i = 1; i < parsedCommand.length; i++) { args.add(parsedCommand[i]); }
            try {
                Class cls = forName(PACKAGE + "." + command);
                Constructor cons = cls.getDeclaredConstructor(Parser.COMMAND_CLASS_PARAMS);
                Object params[] = new Object[]{commandStruct, basicCmd, args, turtle};
                Object obj = cons.newInstance(params);
                Method method = cls.getMethod("executeCommand", Parser.NOPARAMS);
                method.invoke(obj);
            } catch (Exception e) {
                throw new UnknownCommandException(e, ERROR_MESSAGES.getString("UnknownCommand") + command); }
        }
        return parsedCommands;
    }

    @Override
    public void changeLanguage(String lang){
        language = lang;
    }

    @Override
    public String getLanguage(){
        return language;
    }

    public void saveUserDefined(String name) throws IOException {
        Properties prop = new Properties();
        InputStream in = getClass().getResourceAsStream("doc/" + name + ".properties");
        prop.load(in);
        List<VariableStruct> varList = commandStruct.getVariables();
        List<UserCommandStruct> userCommandList = commandStruct.getUserCommands();
        for(VariableStruct v : varList){
            prop.setProperty(v.getName(), Double.toString(v.getValue()));
        }
        for(UserCommandStruct u : userCommandList){
            prop.setProperty(u.getName(), u.getCommandInput());
        }
    }

    public void loadUserDefined(String propFileName){
        ResourceBundle load = ResourceBundle.getBundle(propFileName);
        Enumeration<String> keys = load.getKeys();
        while(keys.hasMoreElements()){
            String key = keys.nextElement();
            if(Character.toString(key.charAt(0)).equals(":")){
                commandStruct.addVariable(new VariableStruct(key, Double.parseDouble(load.getString(key))));
            }
            else{
                commandStruct.addUserCommand(new UserCommandStruct(key, load.getString(key), new ArrayList<>()));
            }
        }
    }
}
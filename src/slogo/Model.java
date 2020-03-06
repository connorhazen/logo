package slogo;

import slogo.commands.Command;
import slogo.exceptions.InvalidParameterException;
import slogo.exceptions.UnknownCommandException;
import slogo.structs.CommandStruct;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import slogo.view.Turtle;

import static java.lang.Class.forName;

public class Model implements ModelInterface{
    private Parser parser;
    private CommandStruct commandStruct;
    private static final String PACKAGE = Command.class.getPackageName();
    private static final ResourceBundle ERROR_MESSAGES = ResourceBundle.getBundle("slogo/exceptions/exception_messages");

    public Model(){
        parser = new Parser();
        commandStruct = new CommandStruct("", this); // TODO: Get language from view
    }

    @Override
    public List<String> runCommand(String input, Turtle turtle) throws UnknownCommandException, InvalidParameterException {
        if(input.trim().equals("")) { throw new UnknownCommandException(ERROR_MESSAGES.getString("NoCommand")); }
        List<String> parsedCommands = parser.parseCommand(input);
        Map<String, String> commandMap = parser.getCommandMap();
        for (String basicCmd : parsedCommands) {
            String[] parsedCommand = basicCmd.split(" ");
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
            } catch (Exception e) { throw new UnknownCommandException(e, ERROR_MESSAGES.getString("UnknownCommand") + command); }
        }
        return parsedCommands;
    }

    public Parser getParser(){
        return parser;
    }
}
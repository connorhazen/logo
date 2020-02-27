package slogo;

import slogo.exceptions.UnknownCommandException;
import slogo.structs.CommandStruct;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.lang.Class.forName;

public class Model implements ModelInterface{
    private Parser parser;
    private CommandStruct commandStruct;

    public Model(){
        parser = new Parser();
        commandStruct = new CommandStruct(""); // TODO: Get language from view
    }

    @Override
    public Turtle runCommand(String input, Turtle turtle) throws UnknownCommandException {
        List<String> parsedCommands = parser.parseCommand(input);
        Map<String, String> commandMap = parser.getCommandMap();
        for (String basicCmd : parsedCommands) {
            String[] parsedCommand = basicCmd.split(" ");
            String command = commandMap.get(parsedCommand[Parser.COMMAND_INDEX]);
            List<String> args = new ArrayList<>();
            for (int i = 1; i < parsedCommand.length; i++) {
                args.add(parsedCommand[i]);
            }
            try {
                Class cls = forName("slogo.commands." + command);
                Constructor cons = cls.getDeclaredConstructor(Parser.COMMAND_CLASS_PARAMS);
                Object params[] = new Object[]{commandStruct, basicCmd, args, turtle};
                Object obj = cons.newInstance(params);
                Method method = cls.getDeclaredMethod("execute", Parser.EXECUTE_CLASS_PARAMS);
                method.setAccessible(true);
                method.invoke(obj, turtle);
            } catch (Exception e) { throw new UnknownCommandException("Command not recognized: " + command); }
        }
        return turtle;
    }
}
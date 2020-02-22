package slogo;

import java.util.HashMap;
import java.util.Map;

public class Parser implements ParserInterface{
    private Map<String, String> savedCommands;

    public Parser(){
        savedCommands = new HashMap<String, String>();
    }

    public String[] parseCommand(String cmd){
        return cmd.split(" ");
    }
}

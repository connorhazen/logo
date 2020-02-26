package slogo.structs;

import slogo.structs.Struct;

import java.util.List;

public class UserCommandStruct extends Struct {
    private String myCommandInput;
    private List<String> myArgs;

    public UserCommandStruct(String name, String label, List<String> args) {
        super(name);
        setCommandInput(label);
        setArgs(args);
    }


    public void setCommandInput(String command) {
        myCommandInput = command;
    }

    public String getCommandInput(){
        return myCommandInput;
    }

    public List<String> getArgs(){
        return myArgs;
    }

    public void setArgs(List<String> args){
        myArgs = args;
    }
}

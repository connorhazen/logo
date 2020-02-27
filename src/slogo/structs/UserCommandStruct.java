package slogo.structs;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import slogo.structs.Struct;

import java.util.ArrayList;
import java.util.List;

public class UserCommandStruct extends Struct {
    private StringProperty myCommandInput;
    private List<String> myArgs;

    public UserCommandStruct(String name, String label, List<String> args) {
        super(name);
        setCommandInput(label);
        setArgs(args);
    }

    public StringProperty commandsProperty()
    {
        if (myCommandInput == null) {
            myCommandInput = new SimpleStringProperty("");
        }
        return myCommandInput;
    }

    public void setCommandInput(String command) {
        commandsProperty().set(command);
    }

    public String getCommandInput(){
        return commandsProperty().get();
    }

    public List<String> getArgs(){
        if(myArgs == null){
            myArgs = new ArrayList<String>();
        }
        return myArgs;
    }

    public void setArgs(List<String> args){
        myArgs = args;
    }
}

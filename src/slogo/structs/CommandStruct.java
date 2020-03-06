package slogo.structs;

import slogo.Model;

import java.util.ArrayList;
import java.util.List;

public class CommandStruct {
    List<VariableStruct> myVariables;
    private List<UserCommandStruct> myUserCommands;
    private String myLanguage;
    private Model myModel;

    public CommandStruct(String language, Model inputModel) {
        myVariables = new ArrayList<VariableStruct>();
        myUserCommands = new ArrayList<UserCommandStruct>();
        myLanguage = language;
        myModel = inputModel;
    }

    public Model getModel(){
        return myModel;
    }

    public List<VariableStruct> getVariables() {
        return myVariables;
    }

    public List<VariableStruct> getStackVariables() {
        for (int i = 0; i < myVariables.size(); i++) {
            VariableStruct v = myVariables.get(i);
            if (v.getStackSize() != 0)
                v.popFromStack(); // iterate through and pop items
            else {
                myVariables.remove(v);
            }
        }
        return myVariables;
    }

    public VariableStruct getVariable(String variableName) {
        for (VariableStruct v : myVariables) {
            if (v.getName().equals(variableName)) {
                return v;
            }
        }
        return null;
    }

    public boolean containsVariable(String variableName) {
        return !(getVariable(variableName) == null);
    }

    public void addVariable(VariableStruct var){
        for (int i = 0; i < myVariables.size(); i++) {
            if (myVariables.get(i).equals(var)) {
                myVariables.remove(i);
                i--;
            }
        }
        myVariables.add(var);
    }

    public UserCommandStruct getUserCommand(String commandName) {
        for (UserCommandStruct usrC : myUserCommands) {
            if (usrC.getName().equals(commandName)) {
                return usrC;
            }
        }
        return null;
    }

    public boolean containsUserCommand(String commandName) {
        return !(getUserCommand(commandName) == null);
    }

    public void addUserCommand(UserCommandStruct usrC){
        for (int i = 0; i < myUserCommands.size(); i++) {
            if (myUserCommands.get(i).equals(usrC)) {
                myUserCommands.remove(i);
                i--;
            }
        }
        myUserCommands.add(usrC);
    }

    public String getLanguage() {
        return myLanguage;
    }

}

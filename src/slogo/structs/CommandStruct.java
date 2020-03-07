package slogo.structs;

import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.paint.Color;
import slogo.Model;
import slogo.view.Turtle;

import java.util.*;

public class CommandStruct {
    private List<VariableStruct> myVariables;
    private List<UserCommandStruct> myUserCommands;
    private Model myModel;
    private Map<Integer,Color> myColorMap = new HashMap<>();
    private SimpleMapProperty<Integer, Turtle> myTurtleMap = new SimpleMapProperty<>();
    private Set<Turtle> myTurtleSet = new HashSet<>();
    private SimpleObjectProperty<Turtle> myActiveTurtle = new SimpleObjectProperty<>();

    public CommandStruct(Model inputModel) {
        myVariables = new ArrayList<VariableStruct>();
        myUserCommands = new ArrayList<UserCommandStruct>();
        myModel = inputModel;

    }

    public Turtle getActiveTurtle(){
        return myActiveTurtle.getValue();
    }

    public void setActiveTurtle(Turtle curTurt){
        myActiveTurtle.setValue(curTurt);
    }

    public Model getModel(){
        return myModel;
    }


    public Turtle getTurtle(int index){ return myTurtleMap.get(index);}

    public boolean turtleExists(int index){
        return myTurtleMap.containsKey(index);
    }

    public int totalTurtles(){
        return myTurtleMap.size();
    }

    public Set<Turtle> getTurtleSet() { return myTurtleSet;}

    public void setTurtleSet(Set<Turtle> turtleSet){
        myTurtleSet = turtleSet;
    }

    public void setTurtleSetDefault(){
        myTurtleSet = (HashSet) myTurtleMap.values();
    }

    public SimpleMapProperty<Integer, Turtle> getTurtleMapProperty(){return myTurtleMap;}

    public boolean addTurtle(Turtle newTurtle){
        myTurtleSet.add(newTurtle);
        myTurtleMap.putIfAbsent(newTurtle.getID(), newTurtle);
        return true;
    }

    public Color getColor(int index){
        return myColorMap.get(index);
    }

    public boolean colorKeyExists(int index){
        return myColorMap.containsKey(index);
    }

    public boolean setColor(int index, int r, int g, int b){
        r = castIntInRange(r);
        g = castIntInRange(g);
        b = castIntInRange(b);
        myColorMap.put(index, Color.rgb(r, g, b));

        return true;
    }

    private int castIntInRange(int val){
        if(val < 0){
            return 0;
        }
        if(val > 255){
            return 255;
        }
        return val;
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

}

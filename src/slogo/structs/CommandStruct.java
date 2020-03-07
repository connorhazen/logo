package slogo.structs;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.paint.Color;
import slogo.Model;
import slogo.view.Turtle;

import java.util.*;

public class CommandStruct {
    private Turtle firstTurtle;
    private List<VariableStruct> myVariables;
    private List<UserCommandStruct> myUserCommands;
    private Model myModel;
    private Map<Integer,Color> myColorMap = new HashMap<>();
    private Map<Integer, Turtle> myTurtleMap = new HashMap<>();
    private Set<Turtle> myTurtleSet = new HashSet<>();
    private SimpleObjectProperty<Turtle> myActiveTurtle = new SimpleObjectProperty<>();

    private int myPenColor = 1;
    private int myPenSize = 3;
    private int myShape = 1;

    private SimpleBooleanProperty changedMap = new SimpleBooleanProperty(false);


    public CommandStruct(Model inputModel) {
        myVariables = new ArrayList<VariableStruct>();
        myUserCommands = new ArrayList<UserCommandStruct>();
        myModel = inputModel;
        setColor(1, 0, 0, 0);
        firstTurtle = null;

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



    public Turtle getTurtleAndAdd(int index){
        if(!myTurtleMap.containsKey(index)){
            Turtle newTurtle = new Turtle(index, 0, 0, -90);
            newTurtle.getPen().setColor(myColorMap.get(myPenColor));
            newTurtle.getPen().setSize(myPenSize);
            newTurtle.setShape(myShape);
            myTurtleMap.put(index, newTurtle);
        }
        return myTurtleMap.get(index);
    }

    public int totalTurtles(){
        return myTurtleMap.size();
    }

    public Set<Turtle> getTurtleSet() { return myTurtleSet;}

    public void setTurtleSet(Set<Turtle> turtleSet){
        myTurtleSet = turtleSet;
    }

    public void deleteAllTurtles(){
        myTurtleMap = new HashMap<>();
        myTurtleSet = new HashSet<>();
    }

    public Set<Turtle> getFullTurtleSet(){return (HashSet) myTurtleMap.values();}

    public Map<Integer, Turtle> getMyTurtleMap(){
        return myTurtleMap;
    }

    public SimpleBooleanProperty getChangedMap(){
        return changedMap;
    }

    public void resetChangedMap(){
        if(changedMap.getValue()){
            changedMap.set(false);
        }
    }

    public boolean addTurtle(Turtle newTurtle){
        if (firstTurtle==null){
            firstTurtle = newTurtle;
        }
        changedMap.set(true);
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

    public void setPenColor(int index){
        myPenColor = index;
    }

    public void setPenSize(int size){
        myPenSize = size;
    }

    public void setPenShape(int index){
        myShape = index;
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
        for(VariableStruct v: myVariables){
            if(v.getName().equals(variableName)) {
                return true;
            }
        }
        return false;
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

    public void reset() {
        System.out.println(firstTurtle.getID());
        firstTurtle.reset();
        myTurtleMap.clear();
        myTurtleSet.clear();
        myTurtleMap.put(firstTurtle.getID(), firstTurtle);
        setActiveTurtle(firstTurtle);
        changedMap.set(true);
    }
}

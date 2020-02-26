package slogo.structs;

import slogo.structs.Struct;

import java.util.Stack;

public class VariableStruct extends Struct {
    private double myValue;
    private Stack<Double> myData;

    public VariableStruct(String name, double data) {
        super(name);
        myData = new Stack<Double>();
        addToStack(data);
        setValue(data);
    }

    public double popFromStack() {
        double ret = myData.pop();
        if (myData.size() != 0) {
            myValue = myData.peek();
        }
        return ret;
    }

    public void addToStack(double data){
        myData.add(data);
    }

    public double getValue(){
        return myValue;
    }

    public void setValue(double data){
        myValue = data;
    }

    public int getStackSize() {
        return myData.size();
    }
}

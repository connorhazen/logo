package slogo.structs;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import slogo.structs.Struct;

import java.util.Stack;

public class VariableStruct extends Struct {
    private DoubleProperty myValue;
    private Stack<Double> myData;

    public VariableStruct(String name, double data) {
        super(name);
        myData = new Stack<Double>();
        addToStack(data);
        setValue(data);
    }

    public DoubleProperty valueProperty() {
        if (myValue == null) {
            myValue = new SimpleDoubleProperty(0);
        }
        return myValue;
    }


    public void setValue(double value) {
        if (!myData.isEmpty())
            myData.set(myData.size() - 1, value);
        valueProperty().set(value);
    }


    public void addToStack(double value) {
        myData.add(value);
    }

    public double getValue() {
        return valueProperty().get();
    }

    public double popFromStack() {
        double toRet = myData.pop();
        if (myData.size() != 0) {
            setValue(myData.peek());
        }
        return toRet;
    }

    public int getStackSize() {
        return myData.size();
    }
}

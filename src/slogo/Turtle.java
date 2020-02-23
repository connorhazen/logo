package slogo;

import javafx.util.Pair;

import java.util.List;

public class Turtle implements TurtleInterface{
    private int X;
    private int Y;
    private double angle;
    private boolean penBool = false;
    private List<Pair<Integer, Integer>> history;

    private static final double threeSixty = 360;


    public Turtle(int xCoor, int yCoor, double orrientation){
        X = xCoor;
        Y = yCoor;
        angle = orrientation % threeSixty;
    }

    @Override
    public boolean setLocation(double xCord, double yCord) {
        return false;
    }

    @Override
    public boolean changeSize(double newSize) {
        return false;
    }

    @Override
    public double getX() {
        return X;
    }

    @Override
    public double getY() {
        return Y;
    }

    @Override
    public boolean setAngle(double newAngle) {
        return false;
    }

    @Override
    public double getAngle(){
        return angle;
    }

    @Override
    public boolean reset() {
        return false;
    }

    @Override
    public boolean setPenDown(boolean penStatus) {
        penBool = penStatus;
        return penStatus;
    }

    @Override
    public List<Pair<Integer, Integer>> getHistory() {
        return history;
    }

    @Override
    public boolean clearHistory() {
        history = null;
        return true;
    }
}

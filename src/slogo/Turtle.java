package slogo;

import javafx.util.Pair;

import java.util.List;

//todo: add checking for if updating turtle location is valid
//todo: add statuses for turtle command execution



public class Turtle implements TurtleInterface{
    private static double myInitialX;
    private static double myInitialY;
    private static double myInitialAngle;
    private static double myInitialSize;

    private static int myID;

    private double myX;
    private double myY;
    private double myAngle;
    private boolean myPenStatus = false;
    private double mySize; // turtles are approximated by a circle shape with the diameter equal to mySize
    private List<Pair<Integer, Integer>> myHistory;

    private static final double threeSixty = 360;


    public Turtle(int ID, double xCoor, double yCoor, double orrientation, double size){
        myInitialX = xCoor;
        myInitialY = yCoor;
        myInitialAngle = orrientation % threeSixty;
        myInitialSize = size;

        myID = ID;

        myX = xCoor;
        myY = yCoor;
        mySize = size;
        myAngle = orrientation % threeSixty;
    }

    @Override
    public int getID(){
        return myID;
    }


    @Override
    public boolean setLocation(double xCord, double yCord) {
        myX = xCord;
        myY = yCord;
        return true;
    }



    @Override
    public boolean changeSize(double newSize) {
        mySize = newSize;
        return true;
    }

    @Override
    public double getSize(){
        return mySize;
    }

    @Override
    public double getX() {
        return myX;
    }

    @Override
    public double getY() {
        return myY;
    }

    @Override
    public void setX(double x) {
        myX = x;
    }

    @Override
    public void setY(double y) {
        myY = y;
    }

    @Override
    public boolean setAngle(double newAngle) {
        myAngle = newAngle % threeSixty;
        return true;
    }

    @Override
    public double getAngle(){
        return myAngle;
    }

    @Override
    public boolean reset() {
        myX = myInitialX;
        myY = myInitialY;
        mySize = myInitialSize;
        myAngle = myInitialAngle;
        myHistory = null;

        return true;
    }

    @Override
    public boolean setPenStatus(boolean penStatus) {
        myPenStatus = penStatus;
        return penStatus;
    }

    @Override
    public boolean getPenStatus(){
        return myPenStatus;
    }

    @Override
    public List<Pair<Integer, Integer>> getHistory() {
        return myHistory;
    }

    @Override
    public boolean clearHistory() {
        myHistory = null;
        return true;
    }
}

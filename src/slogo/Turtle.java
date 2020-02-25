package slogo;


import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Pair;



//todo: add checking for if updating turtle location is valid
//todo: add statuses for turtle command execution



public class Turtle implements TurtleInterface{
    private static double myInitialX;
    private static double myInitialY;
    private static double myInitialAngle;
    private static double myInitialSize;

    private static int myID;

    private SimpleDoubleProperty myX;
    private SimpleDoubleProperty myY;
    private double myAngle;
    private boolean myPenStatus = true; // up when false
    private boolean myVisibilityStatus = true; //visibile when true
    private double mySize; // turtles are approximated by a circle shape with the diameter equal to mySize
    private ObservableList<Object> myHistory;

    private static final double threeSixty = 360;


    public Turtle(int ID, double xCoor, double yCoor, double orrientation, double size){
        myInitialX = xCoor;
        myInitialY = yCoor;
        myInitialAngle = orrientation % threeSixty;
        myInitialSize = size;

        myID = ID;

        myX = new SimpleDoubleProperty(xCoor);
        myY = new SimpleDoubleProperty(yCoor);
        mySize = size;
        myAngle = orrientation % threeSixty;

        myHistory =  FXCollections.observableArrayList();
    }

    @Override
    public int getID(){
        return myID;
    }


    @Override
    public boolean setLocation(double xCord, double yCord) {
        Pair<Double, Double> storeCurLoc = new Pair<>(myX.getValue(), myY.getValue());



        myX.set(xCord);
        myY.set(yCord);

        myHistory.add(storeCurLoc);
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
        return myX.doubleValue();
    }

    @Override
    public double getY() {
        return myY.doubleValue();
    }

    @Override
    public SimpleDoubleProperty getXProperty() {
        return myX;
    }

    @Override
    public SimpleDoubleProperty getYProperty() {
        return myY;
    }

    @Override
    public void setX(double x) {
        myX.set(x);
    }

    @Override
    public void setY(double y) {
        myY.set(y);
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
        setY(myInitialY);
        setX(myInitialX);

        mySize = myInitialSize;
        myAngle = myInitialAngle;
        myHistory .clear();

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
    public ObservableList<Object> getHistory() {
        return myHistory;
    }

    @Override
    public boolean clearHistory() {
        myHistory.clear();
        return true;
    }

    @Override
    public boolean getVisibilityStatus() {
        return myVisibilityStatus;
    }

    @Override
    public boolean setVisibilityStatus(boolean visibilityStatus) {
        myVisibilityStatus = visibilityStatus;
        return myVisibilityStatus;
    }
}

package slogo.view;


import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Pair;

//todo: add checking for if updating turtle location is valid
//todo: add statuses for turtle command execution



public class Turtle implements TurtleInterface {
    private static double myInitialX;
    private static double myInitialY;
    private static double myInitialAngle;

    private SimpleObjectProperty <Coordinates> cords;

    private static int myID;

    private SimpleDoubleProperty myAngle;
    private boolean myPenStatus = true; // up when false
    private SimpleBooleanProperty myVisibilityStatus; //visibile when true
    private ObservableList<Object> myHistory;

    private static final double threeSixty = 360;


    public Turtle(int ID, double xCoor, double yCoor, double orientation){
        myInitialX = xCoor;
        myInitialY = yCoor;
        cords = new SimpleObjectProperty<>(new Coordinates(xCoor, yCoor));
        myInitialAngle = orientation;
        myID = ID;
        myAngle = new SimpleDoubleProperty(orientation);
        myVisibilityStatus = new SimpleBooleanProperty(true);
        myHistory =  FXCollections.observableArrayList();
        Pair<Double, Double> storeCurLoc = new Pair<>(cords.getValue().x, cords.getValue().y);
        myHistory.add(storeCurLoc);
    }

    @Override
    public int getID(){
        return myID;
    }


    @Override
    public boolean setLocation(double xCord, double yCord) {
        Pair<Double, Double> storeCurLoc = new Pair<>(cords.getValue().x, cords.getValue().y);

        cords.set(new Coordinates(xCord, yCord));

        myHistory.add(storeCurLoc);
        return true;
    }

    @Override
    public double getX() {
        return cords.getValue().x;
    }

    @Override
    public double getY() {
        return cords.getValue().y;
    }

    @Override
    public SimpleDoubleProperty getAngleProperty() {
        return myAngle;
    }

    @Override
    public boolean setAngle(double newAngle) {
        myAngle.set(newAngle);
        return true;
    }

    @Override
    public double getAngle(){
        return myAngle.getValue();
    }

    @Override
    public boolean reset() {
        cords.set(new Coordinates(myInitialX, myInitialY));
        myAngle.set(myInitialAngle);
        myVisibilityStatus.set(true);
        myHistory.clear();

        Pair<Double, Double> storeCurLoc = new Pair<>(cords.getValue().x, cords.getValue().y);
        myHistory.add(storeCurLoc);
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
        return myVisibilityStatus.getValue();
    }

    @Override
    public boolean setVisibilityStatus(boolean visibilityStatus) {
        myVisibilityStatus.set(visibilityStatus);
        return getVisibilityStatus();
    }

    @Override
    public SimpleBooleanProperty getVisibleProperty() {
        return myVisibilityStatus;
    }

    @Override
    public SimpleObjectProperty<Coordinates> getCordsProperty() {
        return cords;
    }

}

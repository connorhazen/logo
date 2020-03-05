package slogo.view;


import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
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

    private boolean isActive;
    private SimpleIntegerProperty myShapeIndex = new SimpleIntegerProperty();

    private static final double threeSixty = 360;


    public Turtle(int ID, double xCoor, double yCoor, double orientation){
        isActive = true;
        myInitialX = xCoor;
        myInitialY = yCoor;
        cords = new SimpleObjectProperty<>(new Coordinates(xCoor, yCoor));
        myInitialAngle = orientation;
        myID = ID;
        myShapeIndex.set(0);
        myAngle = new SimpleDoubleProperty(orientation);
        myVisibilityStatus = new SimpleBooleanProperty(true);
        myHistory =  FXCollections.observableArrayList();
        Pair<Double, Double> storeCurLoc = new Pair<>(cords.getValue().getX(), cords.getValue().getY());
        myHistory.add(storeCurLoc);
    }

    @Override
    public int getID(){
        return myID;
    }


    @Override
    public boolean setLocation(double xCord, double yCord) {
        if(isActive == false) return false;
        Pair<Double, Double> storeCurLoc = new Pair<>(cords.getValue().getX(), cords.getValue().getY());

        cords.set(new Coordinates(xCord, yCord));

        myHistory.add(storeCurLoc);
        return true;
    }

    @Override
    public double getX() {
        return cords.getValue().getX();
    }

    @Override
    public double getY() {
        return cords.getValue().getY();
    }

    @Override
    public SimpleDoubleProperty getAngleProperty() {
        return myAngle;
    }

    @Override
    public boolean setAngle(double newAngle) {
        if(isActive == false) return isActive;
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

        Pair<Double, Double> storeCurLoc = new Pair<>(cords.getValue().getX(), cords.getValue().getY());
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

    @Override
    public boolean setShape(int index) {
        myShapeIndex.set(index);
        return true;
    }

    public int getShape(){
        return myShapeIndex.getValue();
    }

    public boolean switchActive(){
        this.isActive = !this.isActive;
        return this.isActive;
    }
}

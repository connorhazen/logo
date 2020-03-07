package slogo.view;


import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.paint.Color;

//todo: add checking for if updating turtle location is valid
//todo: add statuses for turtle command execution



public class Turtle implements TurtleInterface {
    private static double myInitialX;
    private static double myInitialY;
    private static double myInitialAngle;

    private SimpleObjectProperty <Coordinates> cords;

    private static int myID;
    private double lastX;
    private double lastY;

    private SimpleDoubleProperty myAngle;
    private boolean myPenStatus = true; // up when false
    private SimpleBooleanProperty myVisibilityStatus; //visibile when true
    private List<Coordinates> myHistory;

    private boolean isActive;
    private SimpleIntegerProperty myShapeIndex = new SimpleIntegerProperty();
    private Pen myPen = new Pen();
    private Color myBackgroundColor = Color.WHITE;



    public Turtle(int ID, double xCoor, double yCoor, double orientation){

        isActive = true;
        myInitialX = xCoor;
        myInitialY = yCoor;
        lastX = xCoor;
        lastY = yCoor;
        cords = new SimpleObjectProperty<>(new Coordinates(xCoor, yCoor));
        myInitialAngle = orientation;
        myID = ID;
        myShapeIndex.set(0);
        myAngle = new SimpleDoubleProperty(orientation);
        myVisibilityStatus = new SimpleBooleanProperty(true);
        myHistory =  new ArrayList<>();
        myHistory.add(new Coordinates(xCoor, yCoor));
    }

    @Override
    public int getID(){
        return myID;
    }


    @Override
    public boolean setLocation(double xCord, double yCord) {
        if(!isActive) return isActive;
        lastX = cords.getValue().getX();
        lastY = cords.getValue().getX();

        cords.set(new Coordinates(xCord, yCord));

        myHistory.add(cords.getValue());
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
        if(!isActive) return isActive;
        myAngle.set(newAngle);
        return true;
    }

    @Override
    public double getAngle(){
        return myAngle.getValue();
    }

    @Override
    public boolean reset() {
        lastY = myInitialY;
        lastX = myInitialX;
        cords.set(new Coordinates(myInitialX, myInitialY));
        myAngle.set(myInitialAngle);
        myVisibilityStatus.set(true);
        myHistory.clear();

        myHistory.add(cords.getValue());
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
    public List<Coordinates> getHistory() {
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
    public Pen getPen() {
        return myPen;
    }


    @Override
    public double getLastY() {
        return lastY;
    }

    @Override
    public double getLastX() {
        return lastX;
    }

    //TODO: catch out of bounds
    @Override
    public boolean setShape(int index) {
        myShapeIndex.set(index);
        return true;
    }

    public int getShape(){
        return myShapeIndex.getValue();
    }

    public Color getBackgroundColor(){
        return myBackgroundColor;
    }

    public boolean setBackgroundColor(Color color){
        myBackgroundColor = color;
        return true;
    }

    public boolean switchActive(){
        this.isActive = !this.isActive;
        return this.isActive;
    }
}

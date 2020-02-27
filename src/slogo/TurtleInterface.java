package slogo;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;

public interface TurtleInterface {

  int getID();

  boolean setLocation(double xCord, double yCord);

  double getX();

  double getY();

  SimpleDoubleProperty getXProperty();

  SimpleDoubleProperty getYProperty();

  SimpleDoubleProperty getAngleProperty();

  void setX(double x);

  void setY(double y);

  boolean setAngle(double newAngle);

  double getAngle();

  boolean reset();

  boolean setPenStatus(boolean penStatus);

  boolean getPenStatus();

  ObservableList<Object> getHistory();

  boolean clearHistory();

  boolean getVisibilityStatus();

  boolean setVisibilityStatus(boolean visibilityStatus);

  SimpleBooleanProperty getVisibleProperty();

}
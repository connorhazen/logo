package slogo;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.ObservableList;

public interface TurtleInterface {

  int getID();

  boolean setLocation(double xCord, double yCord);

  boolean changeSize(double newSize);

  double getSize();

  double getX();

  double getY();

  SimpleDoubleProperty getXProperty();

  SimpleDoubleProperty getYProperty();

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
}
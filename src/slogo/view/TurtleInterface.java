package slogo.view;

import java.util.List;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;

public interface TurtleInterface {

  int getID();

  boolean setLocation(double xCord, double yCord);

  double getX();

  double getY();

  SimpleDoubleProperty getAngleProperty();

  boolean setAngle(double newAngle);

  double getAngle();

  boolean reset();

  boolean setPenStatus(boolean penStatus);

  boolean getPenStatus();

  List<Coordinates> getHistory();

  boolean clearHistory();

  boolean getVisibilityStatus();

  boolean setVisibilityStatus(boolean visibilityStatus);

  SimpleBooleanProperty getVisibleProperty();

  SimpleObjectProperty<Coordinates> getCordsProperty();

  boolean setShape(int index);

  int getShape();

  Pen getPen();

  double getLastY();

  double getLastX();
}
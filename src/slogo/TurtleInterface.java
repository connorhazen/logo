package slogo;

import java.util.List;
import javafx.util.Pair;

public interface TurtleInterface {

  int getID();

  boolean setLocation(double xCord, double yCord);

  boolean changeSize(double newSize);

  double getSize();

  double getX();

  double getY();

  void setX(double x);

  void setY(double y);

  boolean setAngle(double newAngle);

  double getAngle();

  boolean reset();

  boolean setPenStatus(boolean penStatus);

  boolean getPenStatus();

  List<Pair<Integer, Integer>> getHistory();

  boolean clearHistory();
}
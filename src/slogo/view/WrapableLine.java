package slogo.view;

import java.util.Map;
import java.util.TreeMap;
import javafx.beans.value.ChangeListener;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.util.Pair;

public class WrapableLine extends Group {

  private double startX;
  private double startY;
  private double endX;
  private double endY;
  private Pane canvas;
  private ChangeListener listener;
  private double length;

  public WrapableLine(double startX, double startY, double endX, double endY, Pane canvas) {
    super();

    setEndPoints(startX, startY, endX, endY);
    this.canvas = canvas;
    makeLines();

    canvas.boundsInParentProperty().addListener(e -> {makeLines();});

  }

  private void setEndPoints(double startX, double startY, double endX, double endY) {
    if (startX < endX) {
      this.startX = startX;
      this.endX = endX;
    } else {
      this.startX = endX;
      this.endX = startX;
    }

    if (startY < endY) {
      this.startY = startY;
      this.endY = endY;
    } else {
      this.startY = endY;
      this.endY = startY;
    }
  }

  private void makeLines() {

    this.getChildren().clear();

    Map<Double, Pair<Double, Double>> breakPoints = new TreeMap<>(
        Double::compareTo);

    breakPoints.put(0.0, new Pair<>(startX, startY));

    getXBreaks(breakPoints);
    getYBreaks(breakPoints);

    Pair<Double,Double> last = new Pair<>(startX, startY);

    for(Pair<Double, Double> next : breakPoints.values()){
      this.getChildren().add(drawLine(last.getKey(), last.getValue(), next.getKey(), next.getValue()));
      last = next;

    }
  }

  private Node drawLine(double x1, double y1, double x2, double y2) {
    return new Line(x1%canvas.getWidth(), y1%canvas.getHeight(), x2%canvas.getWidth(), y2%canvas.getHeight());
  }

  private void getYBreaks(Map<Double, Pair<Double, Double>> breakPoints) {
    double diffToEdge;
    double xIndex = startX;
    double yIndex = startY;
    while (true) {
      diffToEdge = canvas.getHeight() - yIndex % canvas.getHeight();
      yIndex = yIndex + diffToEdge;
      xIndex = findXval(yIndex);
      if (yIndex > endY) {
        breakPoints.put(getLength(startX, startY), new Pair<>(endX, endY));
        break;
      }
      breakPoints.put(getLength(xIndex, yIndex), new Pair<>(xIndex, yIndex));
    }

  }

  private void getXBreaks(Map<Double, Pair<Double, Double>> breakPoints) {
    double diffToEdge;
    double xIndex = startX;
    double yIndex = startY;
    while (true) {
      diffToEdge = canvas.getWidth() - xIndex % canvas.getWidth();
      xIndex = xIndex + diffToEdge;
      yIndex = findYval(xIndex);
      if (xIndex > endX) {
        breakPoints.put(getLength(startX, startY), new Pair<>(endX, endY));
        break;
      }
      breakPoints.put(getLength(xIndex, yIndex), new Pair<>(xIndex, yIndex));
    }
  }

  private double findYval(double x1) {
    double yDiff = (x1 - startX) * (endY - startY) / (endX - startX);
    return startY + yDiff;
  }

  private double findXval(double y1) {
    double xDiff = (y1 - startY) * (endX - startX) / (endY - startY);
    return startX + xDiff;
  }


  private double getLength(double x1, double y1) {
    return Math.sqrt(Math.pow(x1 - startX, 2) + Math.pow(y1 - startY, 2));
  }

}


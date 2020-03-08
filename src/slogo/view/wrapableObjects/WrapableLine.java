package slogo.view.wrapableObjects;

import java.util.Map;
import java.util.TreeMap;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import slogo.view.Coordinates;
import slogo.view.PenInterface;


/**
 * This class creates the line object between two coordinates. It handles the necessary screen wrapping
 * and listens for canvas resize to redraw.
 */
public class WrapableLine extends Group {

  private double startX;
  private double startY;
  private double endX;
  private double endY;
  private Pane canvas;
  private PenInterface pen;

  public WrapableLine(double startX, double startY, double endX, double endY, Pane canvas, SimpleDoubleProperty centerX, SimpleDoubleProperty centerY, PenInterface pen) {
    super();

    if(startX  == endX && startY == endY){
      return;
    }

    setEndPoints(startX, startY, endX, endY, centerX.doubleValue(), centerY.doubleValue());
    this.canvas = canvas;
    this.pen = pen;

    centerX.addListener(e -> {
      makeLines();
      setEndPoints(startX, startY, endX, endY, centerX.doubleValue(), centerY.doubleValue());
    });
    centerY.addListener(e -> {
      makeLines();
      setEndPoints(startX, startY, endX, endY, centerX.doubleValue(), centerY.doubleValue());
    });
    makeLines();

  }

  private void setEndPoints(double startX, double startY, double endX, double endY, double centerX, double centerY) {
    this.startX = startX + centerX;
    this.endX = endX + centerX;
    this.startY = startY+ centerY;
    this.endY = endY+ centerY;
  }

  private void makeLines() {

    this.getChildren().clear();

    Map<Double, Coordinates> breakPoints = new TreeMap<>(
        Double::compareTo);

    getXBreaks(breakPoints);
    getYBreaks(breakPoints);

    breakPoints.putIfAbsent(0.0, new Coordinates(startX, startY));
    breakPoints.putIfAbsent(getLength(endX, endY), new Coordinates(endX,endY));

    Coordinates last = null;
    boolean start = true;
    for(Coordinates next : breakPoints.values()){
      if(start){
        last = next;
        start = false;
        continue;
      }
      this.getChildren().add(drawLine(last.getX(), last.getY(), next.getX(), next.getY()));
      start = true;
    }
  }

  private Node drawLine(double x1, double y1, double x2, double y2) {

    double xloc1 = getVisualLoc(x1, canvas.getWidth());
    double yloc1 = getVisualLoc(y1, canvas.getHeight());
    double xloc2 = getVisualLoc(x2, canvas.getWidth());
    double yloc2 = getVisualLoc(y2, canvas.getWidth());
    return new slogoLine(xloc1, yloc1, xloc2, yloc2, pen);
  }

  private double getVisualLoc(double val, double compare){
    double loc1 = val%compare;
    if(loc1<0){
      loc1 = compare+loc1;
    }
    return loc1;
  }

  private void getYBreaks(Map<Double, Coordinates> breakPoints) {
    double minY;
    double maxY;
    if(startY<endY){
      minY = startY;
      maxY=endY;
    }
    else{
      minY = endY;
      maxY = startY;
    }
    double diffToEdge;
    double yIndex = minY;
    while (true) {
      diffToEdge = findDiff(yIndex, canvas.getHeight());
      yIndex = yIndex + diffToEdge - .05;
      if (yIndex > maxY) {
        break;
      }
      breakPoints.putIfAbsent(getLength(findXval(yIndex), yIndex), new Coordinates(findXval(yIndex), yIndex));
      yIndex += .1;
      breakPoints.putIfAbsent(getLength(findXval(yIndex), yIndex), new Coordinates(findXval(yIndex), yIndex));
    }

  }

  private void getXBreaks(Map<Double, Coordinates> breakPoints) {
    double minX;
    double maxX;
    if(startX<endX){
      minX = startX;
      maxX=endX;
    }
    else{
      minX = endX;
      maxX = startX;
    }

    double diffToEdge;
    double xIndex = minX;
    while (true) {
      diffToEdge = findDiff(xIndex, canvas.getWidth());
      xIndex = xIndex + diffToEdge - .05;
      if (xIndex > maxX) {
        break;
      }
      breakPoints.putIfAbsent(getLength(xIndex, findYval(xIndex)), new Coordinates(xIndex, findYval(xIndex)));
      xIndex = xIndex + .1;
      breakPoints.putIfAbsent(getLength(xIndex, findYval(xIndex)), new Coordinates(xIndex, findYval(xIndex)));
    }
  }

  private double findDiff(double index, double size) {
    if(index<0){
      return  -1 * (index%size);
    }
    else{
      return size - (index % size);
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


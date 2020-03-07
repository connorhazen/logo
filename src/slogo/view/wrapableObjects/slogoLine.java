package slogo.view.wrapableObjects;

import javafx.scene.shape.Line;
import slogo.view.PenInterface;

public class slogoLine extends Line {


  public slogoLine(double xloc1, double yloc1, double xloc2, double yloc2, PenInterface pen) {
    super(xloc1, yloc1, xloc2, yloc2);
    this.setFill(pen.getColor());
    this.setStrokeWidth(pen.getSize());

  }
}

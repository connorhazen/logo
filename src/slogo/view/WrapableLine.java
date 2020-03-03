package slogo.view;

import javafx.beans.value.ChangeListener;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

public class WrapableLine extends Group{

  private double startX;
  private double startY;
  private double endX;
  private double endY;
  private Pane canvas;
  private ChangeListener listener;
  public WrapableLine(double startX, double startY, double endX, double endY, Pane canvas) {
    super();
    this.startX = startX;
    this.startY = startY;
    this.endX = endX;
    this.endY = endY;
    this.canvas = canvas;

    listener = (e, ee, eee) -> {
      updateLines();
      System.out.println(e + " " + ee + " " + eee);
    };
    canvas.boundsInParentProperty().addListener(listener);

    this.getChildren().add(new Line(startX, startY, endX, endY));

    updateLines();
  }

  private void updateLines() {
    if(outOfBounds(startX) || outOfBounds(startY) || outOfBounds(endX) || outOfBounds(endY)){
      this.getChildren().clear();
    }

  }

  private boolean outOfBounds(double val) {
    if(val>canvas.getWidth() + canvas.getLayoutX() || val < canvas.getLayoutX()
        || val>canvas.getHeight() + canvas.getLayoutY() || val < canvas.getLayoutY()){
      return true;
    }
    return false;

  }

  public void deleting(){
    canvas.boundsInParentProperty().removeListener(listener);
  }

}


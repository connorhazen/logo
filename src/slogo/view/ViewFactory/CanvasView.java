package slogo.view.ViewFactory;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import slogo.view.MovableElements.BorderPaneLocation;

public class CanvasView<T> implements BorderPaneElement {
  private Pane myCanvas;
  private BorderPaneLocation loc;
  public CanvasView(BorderPaneLocation loc, T canvas)
  {
    myCanvas = (Pane)canvas;
    this.loc = loc;
  }
  public BorderPaneLocation getLoc(){
    return loc;
  }
  @Override
  public Node getElement() {
    Rectangle edges = new Rectangle();
    myCanvas.getChildren().add(edges);
    edges.widthProperty().bind(myCanvas.widthProperty());
    edges.heightProperty().bind(myCanvas.heightProperty());
    edges.setFill(Color.WHITE);

    return myCanvas;
  }
}

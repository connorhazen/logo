package slogo.view.ViewFactory;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import slogo.view.ViewFactory.BorderPaneElement;

public class CanvasView<T> implements BorderPaneElement {
  private Pane myCanvas;
  public CanvasView(T canvas){
    myCanvas = (Pane)canvas;
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

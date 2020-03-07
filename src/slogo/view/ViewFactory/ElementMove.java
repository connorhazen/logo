package slogo.view.ViewFactory;

import java.util.List;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

public class ElementMove implements EventHandler<MouseEvent> {
  private BorderPane borderPane;
  private Node n;
  private List<BorderPaneElement> bpel;
  private BorderPaneElement bpe;
  public ElementMove(BorderPane borderPane, List<BorderPaneElement> bpel, Node n, BorderPaneElement bpe){
    this.borderPane = borderPane;
    this.bpel = bpel;
    this.n = n;
    this.bpe = bpe;
  }
  @Override
  public void handle(MouseEvent e) {
    BorderPaneElement top = null;
    BorderPaneElement right = null;
    BorderPaneElement bottom = null;
    BorderPaneElement left = null;
    BorderPaneElement center = null;
    for(BorderPaneElement q : bpel){
      switch(q.getLoc()){
        case TOP:
          top = q;
        case CENTER:
          center = q;
        case LEFT:
          left = q;
        case RIGHT:
          right = q;
        case BOTTOM:
          bottom = q;
      }

    }
    double xLoc = n.sceneToLocal(e.getSceneX(), e.getSceneY()).getX();
    if(xLoc > n.sceneToLocal(e.getSceneX(), e.getSceneY()).getX()/2){
      switch(bpe.getLoc()){
        case TOP:
          System.out.println("Move top to right");
          borderPane.getChildren().remove(right.getElement());
          borderPane.setRight(top.getElement());
          borderPane.setTop(right.getElement());
          right = top;
          break;
        case RIGHT:
          System.out.println("Move right to bottom");
          borderPane.getChildren().remove(bottom.getElement());
          borderPane.setBottom(right.getElement());
          borderPane.setRight(bottom.getElement());
        case BOTTOM:
          System.out.println("Move bottom to right");
          borderPane.getChildren().remove(right.getElement());
          borderPane.setRight(bottom.getElement());
          borderPane.setBottom(right.getElement());
        case LEFT:
          System.out.println("Move left to bottom");
          borderPane.getChildren().remove(bottom.getElement());
          borderPane.setBottom(left.getElement());
          borderPane.setLeft(bottom.getElement());
      }
    }
  }
}

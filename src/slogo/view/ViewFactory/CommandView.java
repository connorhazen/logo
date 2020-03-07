package slogo.view.ViewFactory;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import slogo.view.BorderPaneLocation;

public class CommandView implements BorderPaneElement {
  private BorderPaneLocation loc;
  public CommandView(BorderPaneLocation loc){
    this.loc = loc;
  }
  public BorderPaneLocation getLoc(){
    return loc;
  }
  @Override
  public Node getElement() {
    VBox left = new VBox();
    left.getStyleClass().add("vbox");
    Label vars = new Label("Saved Variables:");
    TextArea varBox = new TextArea();

    Label coms = new Label("Saved Commands:");
    TextArea ta2 = new TextArea();

    left.getChildren().addAll(vars, varBox, coms, ta2);
    return left;
  }
}

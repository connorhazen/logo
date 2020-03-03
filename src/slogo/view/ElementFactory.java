package slogo.view;

import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import slogo.ControllerInterface;

public class ElementFactory<T> {
  public BorderPaneElement getNode(String nodeType, ControllerInterface con, ViewInterface view,
      Turtle turtle, T ... node){
    Class<TextArea> textAreaType;
    switch(nodeType){
      case "SettingsBar":
        return new SettingsBar(con, view);
      case "CommandView":
        return new CommandView();
      case "RightView":
        if(node[0] instanceof TextArea) return new RightView<>(node[0], node[1]);
      case "CanvasView":
        if(node[0] instanceof Pane) return new CanvasView<>(node[0]);
      case "BottomView":
        return new BottomView<TextArea>((TextArea)node[0], turtle, view);
      default:
        return null;
    }
  }

}

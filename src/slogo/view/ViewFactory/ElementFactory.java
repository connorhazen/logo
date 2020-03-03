package slogo.view.ViewFactory;

import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import slogo.ControllerInterface;
import slogo.view.Turtle;
import slogo.view.ViewInterface;

public class ElementFactory<T> {
  static ElementFactory instance = null;
  private Turtle turtle;
  private ControllerInterface controllerInterface;
  private ViewInterface viewInterface;

  public static ElementFactory startFactory(ControllerInterface c, ViewInterface v, Turtle t){
    if(instance == null) return new ElementFactory(c, v, t);
    return instance;
  }

  private ElementFactory(ControllerInterface c, ViewInterface v, Turtle t){
    this.turtle = t;
    this.controllerInterface = c;
    this.viewInterface = v;
  }

  public BorderPaneElement getNode(String nodeType, T ... node){
    switch(nodeType){
      case "SettingsBar":
        return new SettingsBar(controllerInterface, viewInterface);
      case "CommandView":
        return new CommandView();
      case "RightView":
        if(node[0] instanceof TextArea) return new RightView<>(node[0], node[1]);
      case "CanvasView":
        if(node[0] instanceof Pane) return new CanvasView<>(node[0]);
      case "BottomView":
        return new BottomView<TextArea>((TextArea)node[0], this.turtle, viewInterface);
      default:
        return null;
    }
  }

}

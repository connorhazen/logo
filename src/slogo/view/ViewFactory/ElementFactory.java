package slogo.view.ViewFactory;

import java.util.ArrayList;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javax.print.DocFlavor.STRING;
import slogo.ControllerInterface;
import slogo.view.BorderPaneLocation;
import slogo.view.Turtle;
import slogo.view.ViewInterface;

public class ElementFactory<T> {
  private Turtle turtle;
  private ControllerInterface controllerInterface;
  private ViewInterface viewInterface;
  private ArrayList<String> clickedCommands;

  public static ElementFactory startFactory(ControllerInterface c, ViewInterface v, Turtle t, ArrayList<String> cmds){
    return new ElementFactory(c, v, t, cmds);
  }

  private ElementFactory(ControllerInterface c, ViewInterface v, Turtle t, ArrayList<String> cmds){
    this.turtle = t;
    this.controllerInterface = c;
    this.viewInterface = v;
    this.clickedCommands = cmds;
  }

  public BorderPaneElement getNode(String nodeType, BorderPaneLocation loc, T ... node){
    switch(nodeType){
      case "SettingsBar":
        return new SettingsBar(controllerInterface, viewInterface, loc);
      case "CommandView":
        return new CommandView(loc);
      case "RightView":
        if(node[0] instanceof TextArea) return new RightView<>(this.turtle, clickedCommands, loc, node[0], node[1]);
      case "CanvasView":
        if(node[0] instanceof Pane) return new CanvasView<>(loc, node[0]);
      case "BottomView":
        return new BottomView<TextArea>((TextArea)node[0], this.turtle, viewInterface, loc);
      default:
        return null;
    }
  }

}

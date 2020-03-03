package slogo.view;

import slogo.ControllerInterface;

public class ElementFactory {
  public BorderPaneElement getNode(String nodeType, ControllerInterface con, ViewInterface view){
    switch(nodeType){
      case "SettingsBar":
        return new SettingsBar(con, view);
      case "CommandView":
        return new CommandView();
      default:
        return null;
    }
  }

}

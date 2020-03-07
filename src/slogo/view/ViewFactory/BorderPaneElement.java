package slogo.view.ViewFactory;

import javafx.scene.Node;
import slogo.view.MovableElements.BorderPaneLocation;

public interface BorderPaneElement {
  Node getElement();
  BorderPaneLocation getLoc();
}

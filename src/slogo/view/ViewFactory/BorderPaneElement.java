package slogo.view.ViewFactory;

import javafx.scene.Node;
import slogo.view.BorderPaneLocation;

public interface BorderPaneElement {
  Node getElement();
  BorderPaneLocation getLoc();
}

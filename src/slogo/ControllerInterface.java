package slogo;

import javafx.scene.control.TextArea;

public interface ControllerInterface{


  /**
   * On run button click, calls model.runCommand with input box contents.
   * If catches error, calls view.printError();
   */
  void updateModel(String commandString); //private


  void executeCommand(String commandText);

  void setLanguage(String title);
}
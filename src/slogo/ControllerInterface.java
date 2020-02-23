package slogo;

public interface ControllerInterface{


  /**
   * On run button click, calls model.runCommand with input box contents.
   * If catches error, calls view.printError();
   */
  void updateModel(); //private


}
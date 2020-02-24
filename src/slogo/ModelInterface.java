package slogo;

import exceptions.UnkownCommandException;

public interface ModelInterface{

  /**
   * This method is called by the controller whenever the user clicks the run button.
   * @param input String input to be parsed and executed.
   * @return
   */
  Turtle runCommand(String input, Turtle turtle) throws UnkownCommandException;

}
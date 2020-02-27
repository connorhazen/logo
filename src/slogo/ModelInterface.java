package slogo;

import java.util.List;
import slogo.exceptions.InvalidParameterException;
import slogo.exceptions.UnknownCommandException;
import slogo.view.Turtle;

public interface ModelInterface{

  /**
   * This method is called by the controller whenever the user clicks the run button.
   * @param input String input to be parsed and executed.
   * @return
   */
  List<String> runCommand(String input, Turtle turtle) throws UnknownCommandException, InvalidParameterException;

}
package slogo;

import java.io.IOException;
import java.util.List;
import slogo.exceptions.InvalidParameterException;
import slogo.exceptions.UnknownCommandException;
import slogo.structs.CommandStruct;
import slogo.view.Turtle;

public interface ModelInterface{

  /**
   * This method is called by the controller whenever the user clicks the run button.
   * @param input String input to be parsed and executed.
   * @return List<String> to be added to the history after the command has been run
   */
  List<String> runCommand(String input, Turtle turtle) throws UnknownCommandException, InvalidParameterException;

  /**
   * This method returns the common CommandStruct that is used by the parser to store user-defined variables
   * and commands and by the commands to store turtles.
   * @return CommandStruct used in the model
   */
  CommandStruct getCommandStruct();

  /**
   * This method changes the language of commands recognized by the parser based on user input in the view
   * @param lang String of language for Slogo to recognize
   */
  void changeLanguage(String lang);

  /**
   * This method returns the current language in the Slogo window.
   * @return String containing language of current Slogo window
   */
  String getLanguage();

  /**
   * This method makes a properties file containing variables and commands from the current Slogo run
   * that can be loaded in a future run
   * @param name String containing the path and name of file to be saved to
   */
  void saveUserDefined(String name) throws IOException;

  /**
   * This method loads variables and commands from a properties file to be used in the current Slogo run
   * @param propFileName String containing the name of the properties file to load from
   */
  void loadUserDefined(String propFileName);
}
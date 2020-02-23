package slogo;

public interface ViewInterface {


  /**
   * This command is called after model has executed user command.
   */
  void updateView(Turtle turtle);

  /**
   * Prints given exception message.
   * @param exception
   */
  void printError(Exception exception);

}
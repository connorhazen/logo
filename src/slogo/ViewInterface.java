package slogo;

public interface ViewInterface {

  /**
   * Prints given exception message.
   * @param exception
   */
  void printErrorFromException(Exception exception);

  void setImage(String value);
}
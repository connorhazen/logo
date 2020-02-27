package slogo;

import java.util.List;

public interface ViewInterface {

  /**
   * Prints given exception message.
   * @param exception
   */
  void printErrorFromException(Exception exception);

  void setImage(String value);

  void printHistory(List<String> history);
}
package slogo.view;

import java.util.List;

public interface ViewInterface {

  /**
   * Prints given exception message.
   * @param exception
   */
  void printErrorFromException(Exception exception);

  void setImage(String value);

  void printHistory(List<String> history);

  void updateView(List<String> history);
}
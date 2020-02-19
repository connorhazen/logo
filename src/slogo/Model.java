import exceptions.UnkownCommandException;

public interface Model{

  /**
   * This method is called by the controller whenever the user clicks the run button.
   * @param input String input to be parsed and executed.
   * @return
   */
  boolean runCommand(String input) throws UnkownCommandException;

}
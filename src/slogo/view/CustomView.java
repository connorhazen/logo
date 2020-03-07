package slogo.view;

import java.io.File;
import javafx.stage.Stage;
import slogo.ControllerInterface;

public class CustomView extends View {
  private File myPropertiesPath;
  public CustomView(ControllerInterface cont, Stage primaryStage,
      Turtle turtle, File path) {
    super(cont, primaryStage, turtle);
    myPropertiesPath = path;
  }

  private void loadDataFromProperties(){

  }
}

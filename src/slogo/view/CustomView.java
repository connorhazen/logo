package slogo.view;

import javafx.stage.Stage;
import slogo.ControllerInterface;

public class CustomView extends View {

  public CustomView(ControllerInterface cont, Stage primaryStage,
      Turtle turtle, String path) {
    super(cont, primaryStage, turtle);
    System.out.println("Path");
  }
}

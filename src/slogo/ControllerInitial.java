package slogo;

import javafx.stage.Stage;

public class ControllerInitial implements ControllerInterface{

  public ControllerInitial(Stage primaryStage){
    ViewInterface view = new View(this, primaryStage);
  }

  @Override
  public void updateModel() {

  }
}

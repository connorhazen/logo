package slogo.windows;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.stage.Stage;
import slogo.view.View;
import slogo.view.ViewInterface;

public class BackgroundColor extends Application {
  private Stage myStage;
  private View myView;
  public BackgroundColor(ViewInterface viewInterface) {
    myView = (View)viewInterface;
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    myStage = primaryStage;
    myStage.setScene(new Scene(getColor()));
    myStage.show();
  }

  private ColorPicker getColor(){
    ColorPicker cp = new ColorPicker();
    cp.setOnAction(e -> myView.setBackGroundColor(cp.getValue()));
    return cp;
  }
}

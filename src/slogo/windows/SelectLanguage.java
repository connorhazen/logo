package slogo.windows;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import slogo.ControllerInterface;

public class SelectLanguage extends Application {

  ControllerInterface controllerInterface;

  public SelectLanguage(ControllerInterface cl){
    controllerInterface = cl;
  }

  @Override
  public void start(Stage primaryStage) {
    Stage myStage = primaryStage;
    Scene scene = new Scene(makeVBox(), 90, 300);
    myStage.setScene(scene);
    myStage.show();
  }

  private VBox makeVBox(){
    VBox vb = new VBox();
    vb.getChildren().addAll(
        makeLangButton("Chinese"),
        makeLangButton("English"),
        makeLangButton("French"),
        makeLangButton("German"),
        makeLangButton("Italian"),
        makeLangButton("Portuguese"),
        makeLangButton("Russia"),
        makeLangButton("Spanish"),
        makeLangButton("Urdu"));
    vb.setSpacing(5);
    return vb;
  }

  private Button makeLangButton(String title){
    Button btn = new Button(title);
    btn.setOnAction(e -> controllerInterface.setLanguage(title));
    return btn;
  }
}

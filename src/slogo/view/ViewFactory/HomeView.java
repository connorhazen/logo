package slogo.view.ViewFactory;

import java.io.IOException;
import java.util.Properties;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import slogo.ControllerInitial;
import slogo.ExceptionHelper;
import slogo.view.View;

public class HomeView extends Application {
  private Stage primaryStage;
  private static final String PROPERTIES = "src/slogo/view/home.properties";
  @Override
  public void start(Stage primaryStage){
    this.primaryStage = primaryStage;
    this.primaryStage.setScene(generateScene());
    this.primaryStage.show();
  }

  private Scene generateScene(){
    Properties myProperties = new Properties();
    try{
      myProperties.load(View.class.getResourceAsStream("home.properties"));
    } catch (IOException e){
      new ExceptionHelper().fileNotFound(e);
    }
    VBox myBox = new VBox();
    HBox myHBox = new HBox();
    for(String key : myProperties.keySet().toArray(new String[0])){
      myHBox.getChildren().add(new Label(myProperties.getProperty(key)));
    }
    myBox.getChildren().add(myHBox);
    HBox buttonBox = new HBox();
    buttonBox.getChildren().add(makeButton("Load default", e -> loadDefaultWorkspace()));
    buttonBox.getChildren().add(makeButton("Load custom workspace", e -> loadCustomWorkspace()));
    myBox.getChildren().add(buttonBox);
    return new Scene(myBox);

  }

  private Button makeButton(String title, EventHandler<ActionEvent> myEvent){
    Button myButton = new Button(title);
    myButton.setOnAction(myEvent);
    return myButton;
  }

  private void loadDefaultWorkspace(){
    new ControllerInitial(new Stage());
  }

  private void loadCustomWorkspace(){
   // new ControllerCustom(new Stage(), new FileChooser().showOpenDialog(new Stage()));
  }

}

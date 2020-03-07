package slogo.view;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import slogo.ExceptionHelper;

public class HomeView extends Application {
  private Stage primaryStage;
  private static final String PROPERTIES = "src/slogo/view/home.properties";
  @Override
  public void start(Stage primaryStage) throws Exception {
    this.primaryStage = primaryStage;
    this.primaryStage.setScene();
    this.primaryStage.show();
  }

  private VBox generateScene(){
    Properties myProperties = new Properties();
    try{
      myProperties.load(View.class.getResourceAsStream(PROPERTIES));
    } catch (IOException e){
      new ExceptionHelper().fileNotFound(e);
    }
    VBox myBox = new VBox();
    HBox myHBox = new HBox();
    for(String key : myProperties.keySet().toArray(new String[0])){
      myHBox.getChildren().add(new Label(myProperties.getProperty(key)));
    }
    HBox buttonBox = new HBox();
    buttonBox.getChildren().add()
  }

  private Button makeButton(String title, EventHandler<ActionEvent> myEvent){
    
  }

}

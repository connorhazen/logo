package slogo.windows;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import slogo.ControllerInterface;
import slogo.ExceptionHelper;

public class HelpWindow extends Application {
  Stage myStage;
  private final static String PROPERTIES = "src/slogo/view/help.properties";
  @Override
  public void start(Stage primaryStage){
    myStage = primaryStage;
    Scene myScene = new Scene(createHelpText());
    myStage.setScene(myScene);
    myStage.show();
  }

  private VBox createHelpText(){
    VBox myVbox = new VBox();
    Properties prop = new Properties();
    try{
      prop.load(new FileInputStream(PROPERTIES));
    } catch (IOException fnfe){
      new ExceptionHelper().fileNotFound(fnfe);
    }
    HashMap<String, String> labelAndText = new HashMap<>();
    for(String key : prop.keySet().toArray(new String[0])){
      HBox row = new HBox();
      Label label = new Label(key + ": ");
      Label helpCmd = new Label(prop.getProperty(key));
      row.getChildren().addAll(label, helpCmd);
      myVbox.getChildren().add(row);
    }
    return myVbox;
  }
}

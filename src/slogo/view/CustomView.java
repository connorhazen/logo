package slogo.view;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javax.imageio.IIOException;
import slogo.ControllerInterface;
import slogo.ExceptionHelper;

public class CustomView extends View {
  private File myPropertiesPath;
  public CustomView(ControllerInterface cont, Stage primaryStage,
      Turtle turtle, File path) {
    super(cont, primaryStage, turtle);
    myPropertiesPath = path;
    loadDataFromProperties();
  }

  private void loadDataFromProperties(){
    Properties props = new Properties();
    try{
      props.load(View.class.getResourceAsStream(myPropertiesPath.toString()));
    } catch(IOException e){
      new ExceptionHelper().fileNotFound(e);
    }
    for(String key : props.keySet().toArray(new String[0])){
      if(key.equals("Color")) super.myColor = Color.web(props.getProperty(key));
    }
  }
}

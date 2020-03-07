package slogo.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import slogo.ControllerInterface;
import slogo.ExceptionHelper;
import slogo.structs.CommandStruct;

public class CustomView extends View {
  private File myPropertiesPath;
  public CustomView(ControllerInterface cont, Stage primaryStage,
      Turtle turtle, File path, CommandStruct workSpaceInfo) {
    super(cont, primaryStage, turtle, workSpaceInfo);
    myPropertiesPath = path;
    loadDataFromProperties();
  }

  private void loadDataFromProperties(){
    Properties props = new Properties();
    try{
      InputStream is = new FileInputStream(myPropertiesPath.toString());
      props.load(is);
    } catch(IOException e){
      new ExceptionHelper().fileNotFound(e);
    }
    for(String key : props.keySet().toArray(new String[0])){
      if(key.equals("Color")) super.setBackGroundColor(Color.web(props.getProperty(key)));
      System.out.println(super.myColor.toString());
    }
  }
}

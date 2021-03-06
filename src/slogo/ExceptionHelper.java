package slogo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ExceptionHelper {
  Alert alert;
  private static final String EXCEPTION_MESSAGES = "src/slogo/exception_messages.txt";
  public ExceptionHelper(){
    alert = new Alert(AlertType.ERROR);
    alert.setResizable(true);
  }

  public ExceptionHelper(AlertType at){
    alert = new Alert(at);
  }

  public void fileNotFound(Exception e){
    alert.setContentText("Error message file not found " + e.getMessage());
    alert.show();
  }

  public void reflectionError(Exception e){
    alert.setContentText(getErrorMessage(e));
    alert.show();
  }
  public String getErrorMessage(Exception e){
    Scanner s;
    try{
      s = new Scanner(new File(EXCEPTION_MESSAGES));
      s.useDelimiter(":");
      while(s.hasNext()){
        if(s.next().equals(e.getClass().getCanonicalName())) return s.next();
        else s.nextLine();
      }
    } catch(FileNotFoundException fnfe){
      fileNotFound(fnfe);
    }
    return "Unknown error";
  }

}

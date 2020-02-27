package slogo.windows;

import java.io.File;
import javafx.application.Application;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import slogo.ControllerInterface;
import slogo.ViewInterface;

public class ImageSelection {
  public static ChoiceBox imageDropDown(ViewInterface vI){
    return getComboBox(vI);
  }


  private static ChoiceBox getComboBox(ViewInterface vI){

    File lang = new File("src/resources/turtleImages");

    ChoiceBox <String> cb = new ChoiceBox<>();

    for(File file : lang.listFiles()){
      String [] name = file.getName().split("\\.");
      String name1 = name[0];
      cb.getItems().add(name1);
    }

    cb.setValue(cb.getItems().get(0));
    vI.setImage(cb.getValue());
    cb.setOnAction(e -> {vI.setImage(cb.getValue());});
    return cb;
  }

}

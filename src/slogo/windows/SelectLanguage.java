package slogo.windows;

import java.io.File;

import javafx.scene.control.ChoiceBox;

import slogo.ControllerInterface;

public class SelectLanguage {


  public static ChoiceBox languageDropDown(ControllerInterface cI){
    return getComboBox(cI);
  }


  private static ChoiceBox getComboBox(ControllerInterface cI){

    File lang = new File("src/resources/languages");

    ChoiceBox <String> cb = new ChoiceBox<>();

    for(File file : lang.listFiles()){
      String [] name = file.getName().split("\\.");
      String name1 = name[0];
      if(name1.equals("English")){
        cb.setValue(name1);
      }
      cb.getItems().add(name1);
    }
    cI.setLanguage(cb.getValue());
    cb.setOnAction(e -> {cI.setLanguage(cb.getValue());});
    return cb;
  }


}

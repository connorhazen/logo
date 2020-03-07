package slogo.view.ViewFactory;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Properties;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.HBox;
import slogo.ControllerInterface;
import slogo.ExceptionHelper;
import slogo.view.MovableElements.BorderPaneLocation;
import slogo.view.View;
import slogo.view.ViewInterface;
import slogo.windows.ImageSelection;
import slogo.windows.SelectLanguage;

public class SettingsBar implements BorderPaneElement {

  private static final String PROPERTIES = "buttons.properties";
  ControllerInterface controller;
  ViewInterface view;
  BorderPaneLocation loc;

  public SettingsBar(ControllerInterface controllerInterface, ViewInterface viewInterface, BorderPaneLocation loc){
    controller = controllerInterface;
    view = viewInterface;
    this.loc = loc;
  }
  public BorderPaneLocation getLoc(){
    return loc;
  }
  @Override
  public Node getElement() {
    HBox myBox = new HBox();
    Properties props = new Properties();
    HashMap<String, String> myButtonMap = new HashMap<>();
    try {
      props.load(View.class.getResourceAsStream("buttons.properties"));
      for(String key : props.stringPropertyNames()){
        myButtonMap.put(key, props.getProperty(key));

      }
    } catch (IOException e) {
      new ExceptionHelper().fileNotFound(e);
    }
    Class<?> thisView = View.class;
    Object obj = view;
    for(String key : myButtonMap.keySet()){
      for(Method m : thisView.getDeclaredMethods()){
        if(myButtonMap.get(key).equals(m.getName())){
          Button b = new Button(key);
          b.setOnAction(e -> {
            try {
              m.setAccessible(true);
              m.invoke(obj);
            } catch (IllegalAccessException | InvocationTargetException ex) {
              System.out.println(ex.getLocalizedMessage());
              ExceptionHelper errorHelper = new ExceptionHelper();
              errorHelper.reflectionError(ex);

            }
          });
          myBox.getChildren().add(b);
        }
      }
    }
    myBox.getChildren().addAll(setLanguageWindow(), setImageWindow());
    return myBox;
  }

  private ChoiceBox setImageWindow() {
    return ImageSelection.imageDropDown(view);
  }

  private ChoiceBox setLanguageWindow() {
    return SelectLanguage.languageDropDown(controller);
  }
}

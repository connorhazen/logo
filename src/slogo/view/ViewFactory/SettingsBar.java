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
import slogo.view.View;
import slogo.view.ViewFactory.BorderPaneElement;
import slogo.view.ViewInterface;
import slogo.windows.ImageSelection;
import slogo.windows.SelectLanguage;

public class SettingsBar implements BorderPaneElement {

  private static final String PROPERTIES = "buttons.properties";
  ControllerInterface controller;
  ViewInterface view;

  public SettingsBar(ControllerInterface controllerInterface, ViewInterface viewInterface){
    controller = controllerInterface;
    view = viewInterface;
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
        System.out.println(key);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    Class<?> thisView = View.class;
    Object obj = view;
    for(String key : myButtonMap.keySet()){
      for(Method m : thisView.getDeclaredMethods()){
        if(myButtonMap.get(key).equals(m.getName())){
          System.out.println(myButtonMap.get(key));
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

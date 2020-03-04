package slogo.view.ViewFactory;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Properties;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import slogo.ExceptionHelper;
import slogo.view.BorderPaneLocation;
import slogo.view.Turtle;
import slogo.view.View;
import slogo.view.ViewFactory.BorderPaneElement;
import slogo.view.ViewInterface;

public class BottomView<T> implements BorderPaneElement {
  TextArea inputText;
  Turtle turtle;
  ViewInterface view;
  private BorderPaneLocation loc;
  public BottomView(TextArea ta, Turtle turtle, ViewInterface view, BorderPaneLocation loc){
    inputText = ta;
    this.turtle = turtle;
    this.view = view;
    this.loc = loc;
  }
  public BorderPaneLocation getLoc(){
    return loc;
  }
  @Override
  public Node getElement() {
    HBox myBox = new HBox();
    myBox.getStyleClass().add("hbox-bot");
    myBox.getChildren().add(inputText);
    Properties props = new Properties();
    HashMap<String, String> myButtonMap = new HashMap<>();
    try {
      props.load(View.class.getResourceAsStream("bottom_view.properties"));
      for(String key : props.stringPropertyNames()){
        myButtonMap.put(key, props.getProperty(key));
      }
    } catch (IOException e) {
      e.printStackTrace();
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
              if(m.getName().equals("reset")) m.invoke(obj, turtle);
              else m.invoke(obj);
            } catch (IllegalAccessException | InvocationTargetException ex) {
              System.out.println(ex.getLocalizedMessage());
              ExceptionHelper errorHelper = new ExceptionHelper();
              errorHelper.reflectionError(ex);

            }
          });
          b.getStyleClass().add("run-bot");
          myBox.getChildren().add(b);
        }
      }
    }
    //myBox.getChildren().add(inputText);
    return myBox;
  }
}
